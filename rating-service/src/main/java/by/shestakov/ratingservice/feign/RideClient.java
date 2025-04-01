package by.shestakov.ratingservice.feign;


import by.shestakov.ratingservice.exception.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rides-service",
             url = "${spring.cloud.openfeign.client.config.rides-service.url}",
             configuration = {CustomErrorDecoder.class})
public interface RideClient {
    @GetMapping("/{id}")
    void getById(@PathVariable String id);
}
