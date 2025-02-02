package by.shestakov.ridesservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Document("rides")
public class Ride {
    @Id
    private String id;

    @Field(name = "driver_id")
    private Long driverId;

    @Field(name = "passenger_id")
    private Long passengerId;

    @Field(name = "address_from")
    private String addressFrom;

    @Field(name = "address_destination")
    private String addressDestination;

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
