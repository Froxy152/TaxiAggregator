package by.shestakov.ratingservice.repository;

import by.shestakov.ratingservice.dto.response.AverageRatingResponse;
import by.shestakov.ratingservice.entity.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
    @Aggregation(pipeline = {
        "{ $match: { 'driver_id': ?0 } }",
        "{ $sort: { 'time': -1 } }",
        "{ $limit: ?1 }",
        "{ $group: { _id: null, average: { $avg: '$rate' } } }"
    })
    AverageRatingResponse findAverageRatingByDriverIdByLimit(Long driverId, Integer limit);

    @Aggregation(pipeline = {
        "{ $match: { 'driver_id': ?0, 'rated_by' : 'PASSENGER' } }",
        "{ $group: { _id: null, average: { $avg: '$rate' } } }"
    })
    AverageRatingResponse findAverageRatingByDriverId(Long driverId);

    @Aggregation(pipeline = {
        "{ $match: { 'passenger_id': ?0, 'rated_by' : 'DRIVER'  } }",
        "{ $group: { _id: null, average: { $avg: '$rate' } } }"
    })
    AverageRatingResponse findAverageRatingByPassengerId(Long passengerId);

    Page<Rating> findAll(Pageable pageable);

    Boolean existsByRideId(String rideId);

    Boolean existsByDriverId(Long driverId);
}
