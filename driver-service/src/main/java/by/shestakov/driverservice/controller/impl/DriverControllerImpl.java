package by.shestakov.driverservice.controller.impl;

import by.shestakov.driverservice.controller.DriverOperations;
import by.shestakov.driverservice.dto.request.DriverRequest;
import by.shestakov.driverservice.dto.request.DriverUpdateRequest;
import by.shestakov.driverservice.dto.response.DriverResponse;
import by.shestakov.driverservice.dto.response.PageResponse;
import by.shestakov.driverservice.service.DriverService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1/drivers")
public class DriverControllerImpl implements DriverOperations {
    private final DriverService driverService;

    @GetMapping
    public ResponseEntity<PageResponse<DriverResponse>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        return new ResponseEntity<>(driverService.getAllDrivers(offset, limit),
                HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverResponse> getById(@PathVariable Long id) {
        return new ResponseEntity<>(driverService.getById(id),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DriverResponse> create(@RequestBody @Valid DriverRequest driverRequest) {
        return new ResponseEntity<>(driverService.createDriver(driverRequest),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<DriverResponse> updateDriver(@RequestBody @Valid DriverUpdateRequest driverUpdateRequest,
                                                       @PathVariable Long id) {
        return new ResponseEntity<>(driverService.updateDriver(driverUpdateRequest, id),
                HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DriverResponse> deleteDriver(@PathVariable Long id) {
        driverService.deleteDriver(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
