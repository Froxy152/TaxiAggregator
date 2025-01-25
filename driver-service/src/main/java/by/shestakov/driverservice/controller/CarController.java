package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.CarDto;
import by.shestakov.driverservice.service.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cars")
public class CarController {
    private final CarService carService;

    @PostMapping
    public ResponseEntity<CarDto> create(@RequestBody CarDto carDto, @PathVariable Long id){
        return new ResponseEntity<>(carService.createCar(carDto,id), HttpStatus.CREATED);
    }

}
