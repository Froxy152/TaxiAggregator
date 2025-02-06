package by.shestakov.ratingservice.repository;

import by.shestakov.ratingservice.entity.Rating;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RatingRepository extends MongoRepository<Rating, String> {
    @Aggregation(pipeline = {
        "{ $match: { 'driver._id': ?0 } }",
        "{ $sort: { 'time': -1 } }",
        "{ $limit: ?1 }",
        "{ $group: { _id: null, avgRating: { $avg: '$mark' } } }"
    })
    Double findAverageRatingByDriverId(Long driverId, Integer limit);

    Boolean existsByRideId(String rideId);
}
