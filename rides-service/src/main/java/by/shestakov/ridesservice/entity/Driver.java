package by.shestakov.ridesservice.entity;

import java.util.HashSet;

public class Driver {
    Long id;

    String name;

    String lastName;

    String email;

    String phoneNumber;

    Gender gender;

    HashSet<Long> carsId;

    Boolean isDeleted;
}
