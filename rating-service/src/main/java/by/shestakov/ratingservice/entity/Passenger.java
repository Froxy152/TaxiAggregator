package by.shestakov.ratingservice.entity;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Passenger implements Serializable {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Boolean isDeleted;
}
