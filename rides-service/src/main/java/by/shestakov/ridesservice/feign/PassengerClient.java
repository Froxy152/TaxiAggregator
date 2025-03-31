package by.shestakov.ridesservice.feign;

import static by.shestakov.ridesservice.util.constant.ExceptionMessage.DRIVER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER;
import static by.shestakov.ridesservice.util.constant.ExceptionMessage.PASSENGER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER;

import by.shestakov.ridesservice.dto.response.DriverResponse;
import by.shestakov.ridesservice.dto.response.PassengerResponse;
import by.shestakov.ridesservice.exception.CustomErrorDecoder;
import by.shestakov.ridesservice.exception.FallbackException;
import by.shestakov.ridesservice.exception.FeignNotFoundDataException;
import feign.FeignException;
import io.github.resilience4j.circuitbreaker.CallNotPermittedException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "passenger-service", configuration = {CustomErrorDecoder.class})
public interface PassengerClient {

    @CircuitBreaker(name = "passenger-service-breaker", fallbackMethod = "getPassengerByIdFallbackMethod")
    @GetMapping("/api/v1/passengers/{id}")
    PassengerResponse getPassengerById(@PathVariable Long id);

    default PassengerResponse getPassengerByIdFallbackMethod(Long id, Throwable throwable) {
        if (throwable instanceof FeignException feignClientException) {
            throw new FallbackException(PASSENGER_SERVICE_UNAVAILABLE_CIRCUIT_BREAKER);
        }
        throw new FeignNotFoundDataException(throwable.getMessage());
    }
}
