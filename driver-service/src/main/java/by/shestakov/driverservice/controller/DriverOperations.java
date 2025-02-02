package by.shestakov.driverservice.controller;

import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PathVariable;

public interface DriverOperations {

    @Operation(summary = "get all drivers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Drivers found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<PageResponse<DriverResponse>> getAll(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                               @RequestParam(value = "limit", defaultValue = "5") Integer limit);

    @Operation(summary = "create new driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Driver created"),
            @ApiResponse(responseCode = "409", description = "Driver already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<DriverResponse> create(@RequestBody @Valid DriverRequest driverRequest);

    @Operation(summary = "update driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Driver updated"),
            @ApiResponse(responseCode = "400", description = "Driver is deleted"),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "409", description = "Driver already exists"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(@RequestBody @Valid DriverRequest driverRequest, @PathVariable Long id);

    @Operation(summary = "soft delete driver")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Driver deleted"),
            @ApiResponse(responseCode = "404", description = "Driver not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DriverResponse> deleteDriver(@PathVariable Long id);
}
