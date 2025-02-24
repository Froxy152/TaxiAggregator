package by.shestakov.ratingservice.feign;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service", url = "localhost:8082/api/v1/drivers")
public interface DriverClient {

    @GetMapping("/{id}")
    void getById(@PathVariable Long id);
}
