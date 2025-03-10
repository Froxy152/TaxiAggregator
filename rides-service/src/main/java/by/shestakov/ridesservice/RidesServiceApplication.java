package by.shestakov.ridesservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.test.context.ActiveProfiles;


@ActiveProfiles("default")
@EnableFeignClients
@SpringBootApplication
public class RidesServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(RidesServiceApplication.class, args);
    }

}
