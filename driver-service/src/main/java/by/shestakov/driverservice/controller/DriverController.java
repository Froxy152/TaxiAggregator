package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.entity.Driver;
import by.shestakov.driverservice.service.DriverService;
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
@RequestMapping("api/v1/drivers")
public class DriverController {
   private final DriverService driverService;

   @Operation(summary = "get all drivers")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Drivers found"),
           @ApiResponse(responseCode = "500", description = "Internal server error")
   })
   @GetMapping
   public ResponseEntity<List<DriverResponse>> getAll(){
      return new ResponseEntity<>(driverService.getAllDrivers(), HttpStatus.OK);
   }
   @Operation(summary = "create new driver")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "201", description = "Driver created"),
           @ApiResponse(responseCode = "409", description = "Driver already exists"),
           @ApiResponse(responseCode = "500", description = "Internal server error")
   })
   @PostMapping
   public ResponseEntity<DriverResponse> create(@RequestBody @Valid DriverRequest driverRequest){
      return new ResponseEntity<>(driverService.createDriver(driverRequest), HttpStatus.CREATED);
   }
   @Operation(summary = "update driver")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "200", description = "Driver updated"),
           @ApiResponse(responseCode = "400", description = "Driver is deleted"),
           @ApiResponse(responseCode = "404", description = "Driver not found"),
           @ApiResponse(responseCode = "409", description = "Driver already exists"),
           @ApiResponse(responseCode = "500", description = "Internal server error")
   })
   @PutMapping("/{id}")
   public ResponseEntity<DriverResponse> updateDriver(@RequestBody @Valid DriverRequest driverRequest, @PathVariable Long id){
      return new ResponseEntity<>(driverService.updateDriver(driverRequest,id),HttpStatus.OK);
   }
   @Operation(summary = "soft delete driver")
   @ApiResponses(value = {
           @ApiResponse(responseCode = "204", description = "Driver deleted"),
           @ApiResponse(responseCode = "404", description = "Driver not found"),
           @ApiResponse(responseCode = "500", description = "Internal server error")
   })
   @DeleteMapping("/{id}")
   public ResponseEntity<Void> deleteDriver(@PathVariable Long id){
      driverService.deleteDriver(id);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
   }
}
