package by.shestakov.ratingservice.controller;

import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RatingController {

    @PostMapping
    ResponseEntity<RatingResponse> createNewRating(@RequestBody RatingRequest ratingRequest);

    @GetMapping //todo new dto for double rating
    ResponseEntity<AverageRatingResponse> get(@RequestParam("driverId") Long driverId,
                                              @RequestParam("limit") Integer limit);

    @PatchMapping
    ResponseEntity<RatingResponse> updateCommentUnderReview(@RequestBody String text,
                                                                   @RequestParam(value = "id") String id);
}
