package by.shestakov.ratingservice.controller;

import by.shestakov.ratingservice.dto.RatingRequest;
import by.shestakov.ratingservice.dto.RatingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public interface RatingController {

    @PostMapping
    ResponseEntity<RatingResponse> createNewRating(@RequestBody RatingRequest ratingRequest);

    @GetMapping //todo new dto for double rating
    ResponseEntity<Double> get(@RequestParam("driverId") Long driverId,
                                      @RequestParam("limit") Integer limit);

    @PatchMapping
    ResponseEntity<RatingResponse> updateCommentUnderReview(@RequestBody String text,
                                                                   @RequestParam(value = "id") String id);
}
