package by.shestakov.ratingservice.service;

import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;

public interface RatingService {
    RatingResponse addNewReviewOnRideByDriver(RatingRequest ratingRequest);

    AverageRatingResponse getResultForDriver(Long driverId, Integer limit);

    RatingResponse changeCommentUnderReview(String reviewId, String text);
}
