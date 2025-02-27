package by.shestakov.ratingservice.feign;

import by.shestakov.ratingservice.dto.response.RideResponse;
import by.shestakov.ratingservice.exception.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rides-service", configuration = {CustomErrorDecoder.class})
public interface RideClient {
    @GetMapping("/{id}")
    RideResponse getById(@PathVariable String id);
}
