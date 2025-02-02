package by.shestakov.ridesservice.util.constant;

import org.springframework.beans.factory.annotation.Value;

public final class ApiConstant {
    @Value("${api.key}")
    private static String API_KEY;

    private ApiConstant() {

    }
}
