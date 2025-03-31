package by.shestakov.ratingservice.feign.IntegrationTest;

import by.shestakov.ratingservice.exception.CustomErrorDecoder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@ActiveProfiles("test")
@FeignClient(name = "driver-service-test",
             configuration = {CustomErrorDecoder.class})
public interface DriverClientIntegration {

    @GetMapping
    void getById(@PathVariable Long id);
}
