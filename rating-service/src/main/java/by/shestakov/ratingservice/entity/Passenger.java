package by.shestakov.ratingservice.entity;


import lombok.Data;



@Data
public class Passenger {
    private Long id;

    private String name;

    private String lastName;

    private String email;

    private String phoneNumber;

    private Boolean isDeleted;
}
