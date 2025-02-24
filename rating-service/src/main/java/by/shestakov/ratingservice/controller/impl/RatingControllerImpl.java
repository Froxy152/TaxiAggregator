package by.shestakov.ratingservice.controller.impl;

import by.shestakov.ratingservice.controller.RatingController;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.PageResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @GetMapping
    @Override
    public ResponseEntity<PageResponse<RatingResponse>> getAllReviews(@RequestParam("offset") Integer offset,
                                                                      @RequestParam("limit") Integer limit) {
        return new ResponseEntity<>(ratingService.getAllReviews(offset, limit),
            HttpStatus.OK);
    }

    @Override
    @GetMapping("/{driverId}")
    public ResponseEntity<AverageRatingResponse> getAverageRating(@PathVariable Long driverId,
                                                                  @RequestParam("limit") Integer limit) {
        return new ResponseEntity<>(ratingService.getResultForDriverWithLimit(driverId, limit),
                HttpStatus.OK);
    }

    @PostMapping
    @Override
    public ResponseEntity<RatingResponse> createNewReview(@RequestBody @Valid RatingRequest ratingRequest) {
        return new ResponseEntity<>(ratingService.addNewReviewOnRide(ratingRequest),
                HttpStatus.CREATED);
    }

    @PatchMapping
    public ResponseEntity<RatingResponse> updateCommentUnderReview(@RequestBody String text,
                                                                   @RequestParam(value = "id") String id) {
        return new ResponseEntity<>(ratingService.changeCommentUnderReview(id, text),
                HttpStatus.OK);
    }
}
