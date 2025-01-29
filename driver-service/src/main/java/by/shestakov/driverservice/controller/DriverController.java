package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/drivers")
public class DriverController {
   private final DriverService driverService;

   @GetMapping
   public ResponseEntity<List<DriverResponse>> getAll(){
      return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
   }
   @PostMapping
   public ResponseEntity<DriverResponse> create(@RequestBody DriverRequest driverRequest){
      return new ResponseEntity<>(driverService.createDriver(driverRequest), HttpStatus.CREATED);
   }
}
