package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.DriverDtoRequest;
import by.shestakov.driverservice.dto.response.DriverDtoResponse;
import by.shestakov.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/drivers")
public class DriverController {
   private final DriverService driverService;

   @PostMapping
   public ResponseEntity<DriverDtoResponse> create(@RequestBody DriverDtoRequest driverDtoRequest){
      return new ResponseEntity<>(driverService.createDriver(driverDtoRequest), HttpStatus.CREATED);
   }
}
