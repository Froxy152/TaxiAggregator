package by.shestakov.driverservice.controller.impl;

import by.shestakov.driverservice.controller.CarOperations;
import by.shestakov.driverservice.dto.request.CarRequest;
import by.shestakov.driverservice.dto.response.CarResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import by.shestakov.driverservice.service.CarService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/cars")
public class CarControllerImpl implements CarOperations {
    private final CarService carService;

    @GetMapping
    public ResponseEntity<PageResponse<CarResponse>> getAllCars(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                                @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        return new ResponseEntity<>(carService.getAllCars(offset, limit),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<CarResponse> createCar(@RequestBody @Valid CarRequest carRequest, @RequestParam Long driverId) {
        return new ResponseEntity<>(carService.createCar(carRequest, driverId),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CarResponse> updateCar(@RequestBody @Valid CarRequest carRequest, @PathVariable Long id) {
        return new ResponseEntity<>(carService.updateCar(carRequest, id),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
