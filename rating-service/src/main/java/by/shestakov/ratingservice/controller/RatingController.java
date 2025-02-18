package by.shestakov.ratingservice.controller;

import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.PageResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

public interface RatingController {

    @GetMapping
    ResponseEntity<PageResponse<RatingResponse>> getAllReviews(@RequestParam("offset") Integer offset,
                                               @RequestParam("limit") Integer limit);

    @GetMapping("/{id}")
    ResponseEntity<AverageRatingResponse> getAverageRating(@PathVariable Long driverId,
                                                           @RequestParam("limit") Integer limit);

    @PostMapping
    ResponseEntity<RatingResponse> createNewReview(@RequestBody RatingRequest ratingRequest);

    @PatchMapping
    ResponseEntity<RatingResponse> updateCommentUnderReview(@RequestBody String text,
                                                                   @RequestParam(value = "id") String id);
}
