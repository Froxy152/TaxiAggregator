package by.shestakov.ratingservice.exception;

import com.fasterxml.jackson.core.JsonParser;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.ResponseEntity;

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

        System.out.println(response.toString());

        switch (response.status()) {
            case 404:
                return new TestException(extractBody(response));
            case 500:
                return new TestException("test");
            default:
                return defaultErrorDecoder.decode(s, response);
        }
    }

    public String extractBody(Response response) {

        try {
            if (response.body() != null) {

                String responseBody = new BufferedReader(
                        new InputStreamReader(response.body().asInputStream(), StandardCharsets.UTF_8))
                        .lines()
                        .collect(Collectors.joining());

                if (responseBody.contains("message")) { // думаю это можно будет убрать
                    String subString = responseBody.substring(responseBody.indexOf("message"));
                    String regex = "message\":\"(.*?)\"";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(subString);
                    if (matcher.find()) {
                        subString = matcher.group(1);
                        return subString.trim();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace(); //todo переделать
        }
        return ""; //todo тоже самое
    }
}


