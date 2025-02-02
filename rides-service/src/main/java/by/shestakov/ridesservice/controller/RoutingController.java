package by.shestakov.ridesservice.controller;

import by.shestakov.ridesservice.dto.request.RideRequest;
import by.shestakov.ridesservice.dto.request.RideStatusRequest;
import by.shestakov.ridesservice.dto.response.PageResponse;
import by.shestakov.ridesservice.dto.response.RideResponse;
import by.shestakov.ridesservice.feign.RoutingFeign;
import by.shestakov.ridesservice.repository.TestRepository;
import by.shestakov.ridesservice.service.RideService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("api/v1/rides")
@RequiredArgsConstructor
public class RoutingController {
    private final TestRepository testRepository;
    private final RoutingFeign feign;
    private final RideService rideService;

//    @GetMapping
//    public ResponseEntity<?> listAll(@RequestBody RoutingRequest request){
//        RoutingResponse response = feign.requestDistance(request.points(), request.calc_point(), request.key());
//
//        return ResponseEntity.ok(response.paths().getFirst());
//    }

    @GetMapping
    public ResponseEntity<PageResponse<RideResponse>> getAll(@RequestParam(value = "offset", defaultValue = "0") Integer offset,
                                                             @RequestParam(value = "limit", defaultValue = "2") Integer limit) {
        return new ResponseEntity<>(rideService.getAll(offset, limit),
                HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<RideResponse> create(@RequestBody RideRequest rideRequest) {
        return new ResponseEntity<>(rideService.createRide(rideRequest),
                HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<RideResponse> updateStatus(@RequestBody RideStatusRequest statusRequest,
                                                     @RequestParam(value = "rideId") String rideId) {
        return new ResponseEntity<>(rideService.changeStatus(statusRequest, rideId),
                HttpStatus.OK);
    }


//    @PostMapping
//    public ResponseEntity<?> createTest(@RequestBody Test test){
//        return ResponseEntity.ok(testRepository.save(test));
//    }

}
