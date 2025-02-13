package by.shestakov.ratingservice.controller.impl;

import by.shestakov.ratingservice.controller.RatingController;
import by.shestakov.ratingservice.dto.RatingRequest;
import by.shestakov.ratingservice.dto.RatingResponse;
import by.shestakov.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RequestMapping("api/v1/ratings")
@RestController
public class RatingControllerImpl implements RatingController {
    private final RatingService ratingService;

    @Override
    @GetMapping
    public ResponseEntity<Double> get(@RequestParam("driverId") Long driverId,
                                      @RequestParam("limit") Integer limit) {
        return new ResponseEntity<>(ratingService.getResultForDriver(driverId, limit),
                HttpStatus.OK);
    }

    @PostMapping
    @Override
    public ResponseEntity<RatingResponse> createNewRating(@RequestBody RatingRequest ratingRequest) {
        return new ResponseEntity<>(ratingService.addNewReviewOnRideByDriver(ratingRequest),
                HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<RatingResponse> updateCommentUnderReview(@RequestBody String text,
                                                                   @RequestParam(value = "id") String id) {
        return new ResponseEntity<>(ratingService.changeCommentUnderReview(id, text),
                HttpStatus.OK);
    }
}
