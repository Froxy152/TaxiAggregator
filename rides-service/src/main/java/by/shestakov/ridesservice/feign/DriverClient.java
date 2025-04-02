package by.shestakov.ridesservice.feign;

import static by.shestakov.ridesservice.util.constant.ExceptionMessage.DRIVER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER;

import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.exception.CustomErrorDecoder;
import by.shestakov.ridesservice.exception.FallbackException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service", configuration = {CustomErrorDecoder.class})
public interface DriverClient {

    @CircuitBreaker(name = "driver-service-breaker", fallbackMethod = "getDriverByIdFallbackMethod")
    @GetMapping("/api/v1/drivers/{id}")
    DriverResponse getDriverById(@PathVariable Long id);

    default DriverResponse getDriverByIdFallbackMethod(Long id, Throwable throwable) {
        if (throwable instanceof FeignException feignClientException) {
            throw new FallbackException(DRIVER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER);
        }
        return null;
       // throw new FeignNotFoundDataException(throwable.getMessage());
    }
}
