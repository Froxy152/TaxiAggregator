package by.shestakov.ratingservice.entity;

import by.shestakov.ratingservice.entity.enums.Gender;

import java.io.Serializable;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Driver implements Serializable {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Gender gender;

    private Boolean isDeleted;

    private List<Car> cars;
}
