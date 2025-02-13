package by.shestakov.ratingservice.service.impl;

import by.shestakov.ratingservice.dto.RatingRequest;
import by.shestakov.ratingservice.dto.RatingResponse;
import by.shestakov.ratingservice.dto.response.PassengerResponse;
import by.shestakov.ratingservice.entity.Car;
import by.shestakov.ratingservice.entity.Driver;
import by.shestakov.ratingservice.entity.Passenger;
import by.shestakov.ratingservice.entity.Rating;
import by.shestakov.ratingservice.entity.enums.Gender;
import by.shestakov.ratingservice.exception.DataNotFoundException;
import by.shestakov.ratingservice.exception.OnlyOneCommentOnRideException;
import by.shestakov.ratingservice.feign.PassengerFeignClient;
import by.shestakov.ratingservice.mapper.PassengerMapper;
import by.shestakov.ratingservice.mapper.RatingMapper;
import by.shestakov.ratingservice.repository.RatingRepository;
import by.shestakov.ratingservice.service.RatingService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final RatingMapper ratingMapper;
    private final PassengerFeignClient passengerClient;
    private final PassengerMapper passengerMapper;

    public RatingResponse addNewReviewOnRideByDriver(RatingRequest ratingRequest) {
        // if get ride by id, on ride-service response error 400 bad request btw its maybe not realize
        if (ratingRepository.existsByRideId(ratingRequest.rideId())) {
            throw new OnlyOneCommentOnRideException();
        }

        Rating newRating = ratingMapper.toEntity(ratingRequest);

        Driver driver = Driver.builder()
                .id(225L)
                .name("ilya")
                .lastName("shestakov")
                .email("email")
                .phoneNumber("+375295035305")
                .gender(Gender.MALE)
                .isDeleted(false)
                .cars(List.of(Car.builder()
                                .id(12L)
                                .carBrand("BMW")
                                .driverId(23L)
                                .carColor("Green")
                                .carNumber("H51QW")
                                .isDeleted(false)
                        .build()))
                .build();

        PassengerResponse find = passengerClient.getById(ratingRequest.passengerId());
        Passenger p = passengerMapper.toEntity(find);
        Passenger passenger = Passenger.builder()
                .id(12L)
                .name("abdul")
                .lastName("ahmed")
                .email("asd")
                .phoneNumber("+375442895614")
                .isDeleted(false)
                .build();

        newRating.setDriver(driver);
        newRating.setPassenger(p);

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
    public Double getResultForDriver(Long driverId, Integer limit) { //todo create new dto
        return ratingRepository.findAverageRatingByDriverId(driverId, limit);
    }
}
