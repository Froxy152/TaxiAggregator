package by.shestakov.ratingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class RatingServiceApplication {
	public static void  main(String[] args) {
		SpringApplication.run(RatingServiceApplication.class, args);
	}
}
