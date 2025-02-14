package by.shestakov.ratingservice.entity;

import by.shestakov.ratingservice.entity.enums.Gender;
import java.util.List;
import lombok.Data;


@Data
public class Driver {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private Boolean isDeleted;

    private List<Car> cars;
}
