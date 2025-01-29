package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.service.CarService;
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

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAll(){
        return new ResponseEntity<>(carService.getAllCars(), HttpStatus.OK);
    }

    @PostMapping("/{id}")
    public ResponseEntity<CarResponse> create(@RequestBody @Valid CarRequest carRequest, @PathVariable Long id){
        return new ResponseEntity<>(carService.createCar(carRequest,id), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> update(@RequestBody @Valid CarRequest carRequest, @PathVariable Long id){
        return new ResponseEntity<>(carService.updateCar(carRequest, id), HttpStatus.OK);
    }

}
