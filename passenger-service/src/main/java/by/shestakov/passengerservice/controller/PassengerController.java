package by.shestakov.passengerservice.controller;

import by.shestakov.passengerservice.dto.PassengerDto;
import by.shestakov.passengerservice.service.PassengerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/passengers")
public class PassengerController {

    private final PassengerService passengerService;

    @Operation(summary = "get passenger by id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger found"),
            @ApiResponse(responseCode = "400", description = "Passenger is deleted"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PassengerDto> getById(@PathVariable Long id){
        return new ResponseEntity<>(passengerService.getById(id),HttpStatus.OK);
    }

    @Operation(summary = "create passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Passenger created"),
            @ApiResponse(responseCode = "400", description = "Passenger is deleted"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "409", description = "Passenger with this number or email exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<PassengerDto> create(@RequestBody @Valid PassengerDto passengerDto){
        return new ResponseEntity<>(passengerService.create(passengerDto),HttpStatus.CREATED);
    }
    @Operation(summary = "update passenger")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Passenger updated"),
            @ApiResponse(responseCode = "400", description = "Passenger is deleted"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "409", description = "Passenger with this number or email exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping("/{id}")
    public ResponseEntity<PassengerDto> update(@RequestBody @Valid PassengerDto passengerDto,@PathVariable Long id){
        return new ResponseEntity<>(passengerService.updateByID(passengerDto,id),HttpStatus.OK);
    }
    @Operation(summary = "soft delete passengers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Passenger updated"),
            @ApiResponse(responseCode = "400", description = "Passenger is deleted"),
            @ApiResponse(responseCode = "404", description = "Passenger not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<PassengerDto> delete(@PathVariable Long id){
        passengerService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}