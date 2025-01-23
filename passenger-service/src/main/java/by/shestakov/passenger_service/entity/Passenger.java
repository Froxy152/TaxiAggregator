package by.shestakov.passenger_service.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name =  "passengers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Passenger {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",nullable = false)
    private long id;

    @Column(name = "name",nullable = false)
    private String name;

    @Column(name = "last_name",nullable = false)
    private String lastName;

    @Column(name = "email",nullable = false, unique = true)
    private String email;

    @Column(name = "phone_number",nullable = false,unique = true)
    private String phoneNumber;

    @Column(name = "is_deleted")
    private boolean is_deleted = false;

}
