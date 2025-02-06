package by.shestakov.ratingservice.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@Document("ratings")
public class Rating {
    @MongoId
    private String id;

    private String rideId;

    private Passenger passenger;

    private Driver driver;

    private Integer mark;

    private String commentary;
}
