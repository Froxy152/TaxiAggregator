package by.shestakov.ratingservice.service.impl;

import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.PageResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.entity.Rating;
import by.shestakov.ratingservice.exception.DataNotFoundException;
import by.shestakov.ratingservice.exception.OnlyOneCommentOnRideException;
import by.shestakov.ratingservice.feign.DriverClient;
import by.shestakov.ratingservice.feign.PassengerClient;
import by.shestakov.ratingservice.feign.RideClient;
import by.shestakov.ratingservice.mapper.PageMapper;
import by.shestakov.ratingservice.mapper.RatingMapper;
import by.shestakov.ratingservice.repository.RatingRepository;
import by.shestakov.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;

    private final RatingMapper ratingMapper;

    private final PassengerClient passengerClient;

    private final DriverClient driverClient;

    private final RideClient rideClient;

    private final PageMapper pageMapper;

    @Override
    public PageResponse<RatingResponse> getAllReviews(Integer offset, Integer limit) {
        Page<RatingResponse> ratingPage = ratingRepository.findAll(PageRequest.of(offset, limit))
            .map(ratingMapper::toDto);

        return pageMapper.toDto(ratingPage);
    }

    public RatingResponse addNewReviewOnRide(RatingRequest ratingRequest) {

        checkRide(ratingRequest.rideId());

        if (ratingRepository.existsByRideId(ratingRequest.rideId())) {
            throw new OnlyOneCommentOnRideException();
        }

        Rating newRating = ratingMapper.toEntity(ratingRequest);

        checkDriver(ratingRequest.driverId());

        checkPassenger(ratingRequest.passengerId());

        ratingRepository.save(newRating);

        return ratingMapper.toDto(newRating);
    }

    @Override
    public RatingResponse changeCommentUnderReview(String reviewId, String text) {
        Rating existsRating = ratingRepository.findById(reviewId)
                .orElseThrow((DataNotFoundException::new));

        existsRating.setCommentary(text);

        ratingRepository.save(existsRating);

        return ratingMapper.toDto(existsRating);
    }

    @Override
    public AverageRatingResponse getResultForDriver(Long driverId, Integer limit) {
        return ratingRepository.findAverageRatingByDriverId(driverId, limit);
    }

    private void checkPassenger(Long passengerId) {
        passengerClient.getById(passengerId);
    }

    private void checkDriver(Long driverId) {
        driverClient.getById(driverId);
    }

    private void checkRide(String rideId) {
        rideClient.getById(rideId);
    }
}
