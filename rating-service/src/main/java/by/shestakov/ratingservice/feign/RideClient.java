package by.shestakov.ratingservice.feign;


import static by.shestakov.ratingservice.util.ExceptionMessage.PASSENGER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER;
import static by.shestakov.ratingservice.util.ExceptionMessage.RIDE_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER;

import by.shestakov.ratingservice.exception.CustomErrorDecoder;
import by.shestakov.ratingservice.exception.FallbackException;
import by.shestakov.ratingservice.exception.FeignNotFoundDataException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "rides-service",
             configuration = {CustomErrorDecoder.class})
public interface RideClient {
    @CircuitBreaker(name = "rides-service-breaker", fallbackMethod = "getRideByIdFallbackMethod")
    @GetMapping("/api/v1/rides/{id}")
    void getById(@PathVariable String id);

    default void getRideByIdFallbackMethod(Long id, Throwable throwable) {
        if (throwable instanceof FeignException feignClientException) {
            throw new FallbackException(RIDE_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER);
        }
        throw new FeignNotFoundDataException(throwable.getMessage());
    }
}
