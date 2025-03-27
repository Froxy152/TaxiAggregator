package by.shestakov.ratingservice.service.impl;

import by.shestakov.ratingservice.dto.request.CommentaryDto;
import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.PageResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.entity.RatedBy;
import by.shestakov.ratingservice.entity.Rating;
import by.shestakov.ratingservice.exception.DataNotFoundException;
import by.shestakov.ratingservice.exception.OnlyOneCommentOnRideException;
import by.shestakov.ratingservice.feign.DriverClient;
import by.shestakov.ratingservice.feign.PassengerClient;
import by.shestakov.ratingservice.feign.RideClient;
import by.shestakov.ratingservice.kafka.KafkaProducer;
import by.shestakov.ratingservice.mapper.PageMapper;
import by.shestakov.ratingservice.mapper.RatingMapper;
import by.shestakov.ratingservice.repository.RatingRepository;
import by.shestakov.ratingservice.service.RatingService;
import io.github.resilience4j.retry.annotation.Retry;
import java.math.BigDecimal;
import java.math.RoundingMode;
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

    private final KafkaProducer kafkaProducer;

    @Override
    public PageResponse<RatingResponse> getAllReviews(Integer offset, Integer limit) {
        Page<RatingResponse> ratingPage = ratingRepository.findAll(PageRequest.of(offset, limit))
                .map(ratingMapper::toDto);

        return pageMapper.toDto(ratingPage);
    }

    @Retry(name = "newReview")
    public RatingResponse addNewReviewOnRide(RatingRequest ratingRequest) {
        System.out.println("start creating new review");
        checkRide(ratingRequest.rideId());
        checkDriver(ratingRequest.driverId());
        checkPassenger(ratingRequest.passengerId());

        if (ratingRepository.existsByRideId(ratingRequest.rideId())) {
            throw new OnlyOneCommentOnRideException();
        }

        Rating newRating = ratingMapper.toEntity(ratingRequest);

        ratingRepository.save(newRating);

        if (ratingRequest.ratedBy().equals(RatedBy.PASSENGER)) {
            realtimeDriverUpdateRating(ratingRequest.driverId());
        } else {
            realtimePassengerUpdateRating(ratingRequest.passengerId());
        }

        return ratingMapper.toDto(newRating);
    }

    @Override
    public RatingResponse changeCommentUnderReview(String reviewId, CommentaryDto commentaryDto) {
        Rating existsRating = ratingRepository.findById(reviewId)
                .orElseThrow((DataNotFoundException::new));

        existsRating.setCommentary(commentaryDto.text());

        ratingRepository.save(existsRating);

        return ratingMapper.toDto(existsRating);
    }

    @Override
    public AverageRatingResponse getResultForDriverWithLimit(Long driverId, Integer limit) {
        checkDriver(driverId);
        if (!ratingRepository.existsByDriverId(driverId)) {
            throw new DataNotFoundException();
        }
        return ratingRepository.findAverageRatingByDriverIdByLimit(driverId, limit);
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

    private void realtimeDriverUpdateRating(Long driverId) {
        checkDriver(driverId);

        AverageRatingResponse response = ratingRepository.findAverageRatingByDriverId(driverId);
        if (response.average() != null) {

            BigDecimal rating = BigDecimal.valueOf(response.average());
            rating = rating.setScale(2, RoundingMode.HALF_UP);

            kafkaProducer.sendMessageForDriver(driverId, rating);
        }
    }

    private void realtimePassengerUpdateRating(Long passengerId) {
        checkPassenger(passengerId);

        AverageRatingResponse response = ratingRepository.findAverageRatingByPassengerId(passengerId);
        if (response.average() != null) {
            BigDecimal rating = BigDecimal.valueOf(response.average());
            rating = rating.setScale(2, RoundingMode.HALF_UP);

            kafkaProducer.sendMessageForPassenger(passengerId, rating);
        }
    }


}
