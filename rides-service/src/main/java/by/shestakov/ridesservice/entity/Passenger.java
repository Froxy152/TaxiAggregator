package by.shestakov.ridesservice.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {
    private Long id;
    private String name;
    private String lastName;
    private String email;
    private String phoneNumber;
}
