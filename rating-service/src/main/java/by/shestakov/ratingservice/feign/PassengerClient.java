package by.shestakov.ratingservice.feign;

import by.shestakov.ratingservice.dto.response.PassengerResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "passenger-service", url = "localhost:8081/api/v1/passengers")
public interface PassengerClient {
    @GetMapping("/{id}")
    PassengerResponse getById(@PathVariable Long id);
}
