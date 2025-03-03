package by.shestakov.ratingservice.feign;


import by.shestakov.ratingservice.exception.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "passenger-service",
             url = "${spring.cloud.openfeign.client.config.passenger-service.url}",
             configuration = {CustomErrorDecoder.class})
public interface PassengerClient {
    @GetMapping("/{id}")
    void getById(@PathVariable Long id);
}
