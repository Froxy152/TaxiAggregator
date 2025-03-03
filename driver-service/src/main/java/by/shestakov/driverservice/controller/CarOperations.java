package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.request.UpdateCarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface CarOperations {
    @Operation(summary = "get all cars")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Cars found"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    ResponseEntity<PageResponse<CarResponse>> getAllCars(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit);

    @Operation(summary = "create new car")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Car created"),
                      @ApiResponse(responseCode = "404", description = "Driver for this car not found"),
                      @ApiResponse(responseCode = "409", description = "Car already exists"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping
    ResponseEntity<CarResponse> createCar(@RequestBody @Valid CarRequest carRequest, @RequestParam Long id);

    @Operation(summary = "update car")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Car updated"),
                      @ApiResponse(responseCode = "400", description = "Car is deleted"),
                      @ApiResponse(responseCode = "404", description = "Car not found"),
                      @ApiResponse(responseCode = "409", description = "Car already exists"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PutMapping("/{id}")
    ResponseEntity<CarResponse> updateCar(@RequestBody @Valid UpdateCarRequest carRequest,
                                          @PathVariable Long driverId);

    @Operation(summary = "soft delete car")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "Car deleted"),
                      @ApiResponse(responseCode = "404", description = "Car not found"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteCar(@PathVariable Long id);
}
