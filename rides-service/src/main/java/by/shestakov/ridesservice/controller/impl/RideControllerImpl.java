package by.shestakov.ridesservice.controller.impl;

import by.shestakov.ridesservice.controller.RideController;
import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/rides")
@RequiredArgsConstructor
public class RideControllerImpl implements RideController {
    private final RideService rideService;

    @GetMapping
    public ResponseEntity<PageResponse<RideResponse>> getAll(
            @RequestParam(value = "offset", defaultValue = "0") Integer offset,
            @RequestParam(value = "limit", defaultValue = "2") Integer limit) {
        return new ResponseEntity<>(rideService.getAll(offset, limit),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RideResponse> create(@RequestBody RideRequest rideRequest) {
        return new ResponseEntity<>(rideService.createRide(rideRequest),
                HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<RideResponse> updateStatus(@RequestBody RideStatusRequest statusRequest,
                                                     @RequestParam(value = "rideId") String rideId) {
        return new ResponseEntity<>(rideService.changeStatus(statusRequest, rideId),
                HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<RideResponse> updateRide(@RequestBody RideRequest rideRequest,
                                            @RequestParam(value = "rideId") String id) {
        return new ResponseEntity<>(rideService.updateRide(rideRequest, id),
                HttpStatus.OK);
    }

}
