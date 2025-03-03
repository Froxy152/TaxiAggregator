package by.shestakov.ratingservice.service;

import by.shestakov.ratingservice.dto.request.CommentaryDto;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.PageResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import javax.xml.stream.events.Comment;

public interface RatingService {

    PageResponse<RatingResponse> getAllReviews(Integer offset, Integer limit);

    RatingResponse addNewReviewOnRide(RatingRequest ratingRequest);

    AverageRatingResponse getResultForDriverWithLimit(Long driverId, Integer limit);

    RatingResponse changeCommentUnderReview(String reviewId, CommentaryDto commentaryDto);
}
