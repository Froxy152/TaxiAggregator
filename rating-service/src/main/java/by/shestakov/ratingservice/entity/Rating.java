package by.shestakov.ratingservice.entity;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("reviews")
public class Rating  {
    @MongoId
    private String id;
    @Field("rideId")
    private String rideId;
    @Field("passenger")
    private Passenger passenger;
    @Field("driver")
    private Driver driver;
    @Field("mark")
    private Integer mark;
    @Field("commentary")
    private String commentary;
}
