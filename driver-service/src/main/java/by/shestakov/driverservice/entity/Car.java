package by.shestakov.driverservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_brand", nullable = false)
    private String carBrand;

    @Column(name = "car_number", nullable = false, unique = true)
    private String car_Number;

    @Column(name = "car_color", nullable = false)
    private String carColor;
}
