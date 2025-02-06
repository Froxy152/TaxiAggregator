package by.shestakov.ratingservice.service;

import by.shestakov.ratingservice.dto.RatingRequest;
import by.shestakov.ratingservice.dto.RatingResponse;

public interface RatingService {
    RatingResponse addNewReviewOnRideByDriver(RatingRequest ratingRequest);

    Double getResultForDriver(Long driverId, Integer limit);

    RatingResponse changeCommentUnderReview(String reviewId, String text);
}
