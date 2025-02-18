package by.shestakov.ridesservice.entity;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("rides")
public class Ride {
    @Id
    private String id;

    @Field(name = "driver")
    private Driver driver;

    @Field(name = "passenger")
    private Passenger passenger;

    @Field(name = "pickup_address")
    private String pickUpAddress;

    @Field(name = "destination_address")
    private String destinationAddress;

    @Field(name = "status", targetType = FieldType.INT32)
    private Status status;

    @Field(name = "distance")
    private Double distance;

    @Field(name = "time")
    private LocalDateTime time;

    @Field(name = "during_ride")
    private Integer duringRide;

    @Field(name = "price")
    private Double price;
}
