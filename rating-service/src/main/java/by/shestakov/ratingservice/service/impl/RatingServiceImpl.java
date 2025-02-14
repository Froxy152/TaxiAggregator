package by.shestakov.ratingservice.service.impl;

import by.shestakov.ratingservice.dto.request.RatingRequest;
import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.dto.response.RatingResponse;
import by.shestakov.ratingservice.dto.response.DriverResponse;
import by.shestakov.ratingservice.dto.response.PassengerResponse;
import by.shestakov.ratingservice.entity.Driver;
import by.shestakov.ratingservice.entity.Passenger;
import by.shestakov.ratingservice.entity.Rating;
import by.shestakov.ratingservice.exception.DataNotFoundException;
import by.shestakov.ratingservice.exception.OnlyOneCommentOnRideException;
import by.shestakov.ratingservice.feign.DriverClient;
import by.shestakov.ratingservice.feign.PassengerClient;
import by.shestakov.ratingservice.mapper.DriverMapper;
import by.shestakov.ratingservice.mapper.PassengerMapper;
import by.shestakov.ratingservice.mapper.RatingMapper;
import by.shestakov.ratingservice.repository.RatingRepository;
import by.shestakov.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final PassengerClient passengerClient;
    private final PassengerMapper passengerMapper;
    private final DriverClient driverClient;
    private final DriverMapper driverMapper;

    public RatingResponse addNewReviewOnRideByDriver(RatingRequest ratingRequest) {
        //todo if get ride by id, on ride-service response error 400 bad request btw its maybe not realize
        if (ratingRepository.existsByRideId(ratingRequest.rideId())) {
            throw new OnlyOneCommentOnRideException();
        }

        Rating newRating = ratingMapper.toEntity(ratingRequest);

        DriverResponse driverResponse = driverClient.getById(ratingRequest.driverId());
        Driver driver = driverMapper.toEntity(driverResponse);

        PassengerResponse passengerResponse = passengerClient.getById(ratingRequest.passengerId());
        Passenger passenger = passengerMapper.toEntity(passengerResponse);

        newRating.setDriver(driver);
        newRating.setPassenger(passenger);

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
    public AverageRatingResponse getResultForDriver(Long driverId, Integer limit) { //todo create new dto
        return ratingRepository.findAverageRatingByDriverId(driverId, limit);
    }
}
