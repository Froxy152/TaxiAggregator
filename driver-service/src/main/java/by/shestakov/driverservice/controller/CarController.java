package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cars")
public class CarController {
    private final CarService carService;

    @Operation(summary = "get all cars")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cars found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars(){
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @Operation(summary = "create new car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "car created"),
            @ApiResponse(responseCode = "404", description = "Driver for this car not found"),
            @ApiResponse(responseCode = "409", description = "Car already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{id}")
    public ResponseEntity<CarResponse> createCar(@RequestBody @Valid CarRequest carRequest, @PathVariable Long id){
        return new ResponseEntity<>(carService.createCar(carRequest,id), HttpStatus.CREATED);
    }

    @Operation(summary = "update car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Car updated"),
            @ApiResponse(responseCode = "400", description = "Car is deleted"),
            @ApiResponse(responseCode = "404", description = "Car not found"),
            @ApiResponse(responseCode = "409", description = "Car already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@RequestBody @Valid CarRequest carRequest, @PathVariable Long id){
        return new ResponseEntity<>(carService.updateCar(carRequest, id), HttpStatus.OK);
    }

    @Operation(summary = "soft delete car")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Car deleted"),
            @ApiResponse(responseCode = "404", description = "Car not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
