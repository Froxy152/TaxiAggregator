package by.shestakov.ridesservice.feign;

import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.exception.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ActiveProfiles("test")
@FeignClient(name = "driver-service", configuration = {CustomErrorDecoder.class})
public interface DriverClient {

    @GetMapping("/{id}")
    DriverResponse getDriverById(@PathVariable Long id);

}
