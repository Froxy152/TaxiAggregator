package by.shestakov.ratingservice.controller.impl;

import by.shestakov.ratingservice.controller.RatingController;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.PageResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("api/v1/ratings")
@RestController
public class RatingControllerImpl implements RatingController {
    private final RatingService ratingService;


    @Override
    @GetMapping
    public ResponseEntity<PageResponse<RatingResponse>> getAllReviews(@RequestParam("offset") Integer offset, 
                                                                      @RequestParam("limit") Integer limit) {
        return new ResponseEntity<>(ratingService.getAllReviews(offset, limit),
            HttpStatus.OK);
    }

    @Override
    @GetMapping
    public ResponseEntity<AverageRatingResponse> getAverageRating(@RequestParam("driverId") Long driverId,
                                                                  @RequestParam("limit") Integer limit) {
        return new ResponseEntity<>(ratingService.getResultForDriver(driverId, limit),
                HttpStatus.OK);
    }

    @PostMapping
    @Override
    public ResponseEntity<RatingResponse> createNewReview(@RequestBody RatingRequest ratingRequest) {
        return new ResponseEntity<>(ratingService.addNewReviewOnRide(ratingRequest),
                HttpStatus.CREATED);
    }

    @PatchMapping
    @Override
    public ResponseEntity<RatingResponse> updateCommentUnderReview(@RequestBody String text,
                                                                   @RequestParam(value = "id") String id) {
        return new ResponseEntity<>(ratingService.changeCommentUnderReview(id, text),
                HttpStatus.OK);
    }
}
