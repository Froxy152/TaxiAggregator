package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.DriverDto;
import by.shestakov.driverservice.service.DriverService;
import by.shestakov.driverservice.service.impl.DriverServiceImpl;
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
   public ResponseEntity<DriverDto> create(@RequestBody DriverDto driverDto){
      return new ResponseEntity<>(driverService.createDriver(driverDto), HttpStatus.CREATED);
   }
}
