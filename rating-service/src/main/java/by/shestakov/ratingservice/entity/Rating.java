package by.shestakov.ratingservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("reviews")
public class Rating  {
    @MongoId
    private String id;

    @Field("ride_id")
    private String rideId;

    @Field("passenger_id")
    private Long passengerId;

    @Field("driver_id")
    private Long driverId;

    @Field("mark")
    private Integer mark;

    @Field("commentary")
    private String commentary;
}
