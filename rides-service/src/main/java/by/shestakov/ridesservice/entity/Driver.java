package by.shestakov.ridesservice.entity;


import java.util.Set;
import lombok.Data;

@Data
public class Driver {
    Long id;

    String name;

    String lastName;

    String email;

    String phoneNumber;

    Gender gender;

    Set<Car> carIds;

    Boolean isDeleted;
}
