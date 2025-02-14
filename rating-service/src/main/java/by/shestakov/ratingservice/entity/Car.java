package by.shestakov.ratingservice.entity;

import lombok.Data;


@Data
public class Car {
    private Long id;

    private String carBrand;

    private String carNumber;

    private String carColor;

    private Long driverId;

    private Boolean isDeleted;
}
