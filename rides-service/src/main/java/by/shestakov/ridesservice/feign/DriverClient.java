package by.shestakov.ridesservice.feign;

import by.shestakov.ridesservice.dto.response.DriverResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service", url = "localhost:8082/api/v1/drivers")
public interface DriverClient {

    @GetMapping("/{id}")
    ResponseEntity<DriverResponse> getDriverById(@PathVariable Long id);

}
