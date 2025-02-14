package by.shestakov.ratingservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rides-service", url = "localhost:8083/api/v1/rides")
public interface RideClient {
    @GetMapping("/{id}")
    RideResponse getById(@PathVariable String id);
}
