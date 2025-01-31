package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface CarOperations {
    @Operation(summary = "get all cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<PageResponse<CarResponse>> getAllCars(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                                @RequestParam(value = "limit", defaultValue = "5") Integer limit);

    @Operation(summary = "create new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Car created"),
            @ApiResponse(responseCode = "404", description = "Driver for this car not found"),
            @ApiResponse(responseCode = "409", description = "Car already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody @Valid CarRequest carRequest, @RequestParam Long id);

    @Operation(summary = "update car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car updated"),
            @ApiResponse(responseCode = "400", description = "Car is deleted"),
            @ApiResponse(responseCode = "404", description = "Car not found"),
            @ApiResponse(responseCode = "409", description = "Car already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@RequestBody @Valid CarRequest carRequest, @PathVariable Long driverId);

    @Operation(summary = "soft delete car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Car deleted"),
            @ApiResponse(responseCode = "404", description = "Car not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id);
}
