package by.shestakov.ratingservice.entity;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class Passenger {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
}
