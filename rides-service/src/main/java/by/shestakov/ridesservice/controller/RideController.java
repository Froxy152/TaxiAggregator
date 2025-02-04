package by.shestakov.ridesservice.controller;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RideController {

    @Operation(summary = "get ride page")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ride's found"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @GetMapping
    ResponseEntity<PageResponse<RideResponse>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "2") Integer limit);

    @Operation(summary = "create a ride")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "201", description = "Ride created"),
                      @ApiResponse(responseCode = "400", description = "RideRequest has bad field"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PostMapping
    ResponseEntity<RideResponse> create(@RequestBody RideRequest rideRequest);

    @Operation(summary = "Change status in a ride")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Status changed"),
                      @ApiResponse(responseCode = "400", description = "RideStatusRequest incorrect"),
                      @ApiResponse(responseCode = "404", description = "Ride not found"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PatchMapping
    ResponseEntity<RideResponse> updateStatus(@RequestBody RideStatusRequest statusRequest,
                                              @RequestParam(value = "rideId") String rideId);

    @Operation(summary = "Update a ride")
    @ApiResponses(
            value = { @ApiResponse(responseCode = "200", description = "Ride updated"),
                      @ApiResponse(responseCode = "400", description = "RideRequest has bad field"),
                      @ApiResponse(responseCode = "404", description = "Ride not found"),
                      @ApiResponse(responseCode = "500", description = "Internal server error")})
    @PutMapping
    ResponseEntity<RideResponse> updateRide(@RequestBody RideRequest rideRequest,
                                            @RequestParam(value = "rideId") String id);
}
