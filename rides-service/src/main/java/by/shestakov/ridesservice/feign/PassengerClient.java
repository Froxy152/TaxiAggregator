package by.shestakov.ridesservice.feign;

import by.shestakov.ridesservice.dto.response.PassengerResponse;
import by.shestakov.ridesservice.exception.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ActiveProfiles("test")
@FeignClient(name = "passenger-service", configuration = {CustomErrorDecoder.class})
public interface PassengerClient {

    @GetMapping("/{id}")
    PassengerResponse getPassengerById(@PathVariable Long id);
}
