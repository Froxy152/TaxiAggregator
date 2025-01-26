package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.CarDtoRequest;
import by.shestakov.driverservice.dto.response.CarDtoResponse;
import by.shestakov.driverservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cars")
public class CarController {
    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarDtoResponse> create(@RequestBody CarDtoRequest carDtoRequest, @PathVariable Long id){
        return new ResponseEntity<>(carService.createCar(carDtoRequest,id), HttpStatus.CREATED);
    }

}
