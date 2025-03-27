package by.shestakov.ratingservice.exception;

import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        return switch (response.status()) {
            case 404 -> new FeignNotFoundDataException(extractBody(response));
            default -> defaultErrorDecoder.decode(s, response);
        };
    }


    public String extractBody(Response response) {

        try {
            if (response.body() != null) {

                String responseBody = new BufferedReader(
                        new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining());

                String subString = responseBody.substring(responseBody.indexOf("message"));
                String regex = "message\":\"(.*?)\"";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(subString);
                if (matcher.find()) {
                    subString = matcher.group(1);
                    return subString.trim();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "no message in body";
    }
}


