package by.shestakov.ridesservice.entity;


import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
