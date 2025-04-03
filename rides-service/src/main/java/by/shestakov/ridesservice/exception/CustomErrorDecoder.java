package by.shestakov.ridesservice.exception;

import by.shestakov.ridesservice.util.constant.RegexpConstant;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomErrorDecoder implements ErrorDecoder {
    private final ErrorDecoder defaultErrorDecoder = new Default();

    @Override
    public Exception decode(String s, Response response) {

        return switch (response.status()) {
            case 404 -> new FeignNotFoundDataException(extractMessage(response));
            default -> defaultErrorDecoder.decode(s, response);
        };
    }

    private String extractMessage(Response response) {
        try {
            if (response.body() != null) {
                String responseBody = new BufferedReader(new InputStreamReader(response.body()
                        .asInputStream()))
                        .lines()
                        .collect(Collectors.joining());

                String body = responseBody.substring(responseBody.indexOf("message"));
                Pattern regexp = Pattern.compile(RegexpConstant.EXTRACT_EXCEPTION_MESSAGE_FROM_ANOTHER_SERVICE);
                Matcher matcher = regexp.matcher(body);
                if (matcher.find()) {
                    body = matcher.group(1);
                    return body.trim();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "no message in body";
    }
}
