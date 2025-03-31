package by.shestakov.ratingservice.feign;

import static by.shestakov.ratingservice.util.ExceptionMessage.DRIVER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER;

import by.shestakov.ratingservice.exception.CustomErrorDecoder;
import by.shestakov.ratingservice.exception.FallbackException;
import by.shestakov.ratingservice.exception.FeignNotFoundDataException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "driver-service",
             configuration = {CustomErrorDecoder.class})
public interface DriverClient {
    @CircuitBreaker(name = "driver-service-breaker", fallbackMethod = "getDriverByIdFallbackMethod")
    @GetMapping("/api/v1/drivers/{id}")
    void getById(@PathVariable Long id);

    default void getDriverByIdFallbackMethod(Long id, Throwable throwable) {
        if (throwable instanceof FeignException feignClientException) {
            throw new FallbackException(DRIVER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER);
        }
        throw new FeignNotFoundDataException(throwable.getMessage());
    }
}
