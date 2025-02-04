package by.shestakov.passengerservice.controller;

import by.shestakov.passengerservice.dto.request.PassengerRequest;
import by.shestakov.passengerservice.dto.response.PageResponse;
import by.shestakov.passengerservice.dto.response.PassengerResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;


public interface ControllerAction {
    @Operation(summary = "get all passengers")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Passengers found"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    ResponseEntity<PageResponse<PassengerResponse>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") @Min(0) Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") @Min(1) Integer limit);

    @Operation(summary = "get passenger by id")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Passenger found"),
                      @ApiResponse(responseCode = "400", description = "Passenger is deleted"),
                      @ApiResponse(responseCode = "404", description = "Passenger not found"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping("/{id}")
    ResponseEntity<PassengerResponse> getById(@PathVariable Long id);

    @Operation(summary = "create passenger")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Passenger created"),
                      @ApiResponse(responseCode = "400", description = "Passenger is deleted"),
                      @ApiResponse(responseCode = "404", description = "Passenger not found"),
                      @ApiResponse(responseCode = "409", description = "Passenger with this number or email exists"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping
    ResponseEntity<PassengerResponse> create(@RequestBody @Valid PassengerRequest passengerRequest);

    @Operation(summary = "update passenger")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Passenger updated"),
                      @ApiResponse(responseCode = "400", description = "Passenger is deleted"),
                      @ApiResponse(responseCode = "404", description = "Passenger not found"),
                      @ApiResponse(responseCode = "409", description = "Passenger with this number or email exists"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PutMapping("/{id}")
    ResponseEntity<PassengerResponse> update(
            @RequestBody @Valid PassengerRequest passengerRequest, @PathVariable Long id);

    @Operation(summary = "soft delete passengers")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "204", description = "Passenger updated"),
                      @ApiResponse(responseCode = "400", description = "Passenger is deleted"),
                      @ApiResponse(responseCode = "404", description = "Passenger not found"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @DeleteMapping("/{id}")
    ResponseEntity<PassengerResponse> delete(@PathVariable Long id);
}
