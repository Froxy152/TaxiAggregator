package by.shestakov.driverservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car {

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", carBrand='" + carBrand + '\'' +
                ", car_Number='" + carNumber + '\'' +
                ", carColor='" + carColor + '\'' +
                ", driver=" + driver +
                ", isDeleted=" + isDeleted +
                '}';
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "car_brand", nullable = false)
    private String carBrand;

    @Column(name = "car_number", nullable = false, unique = true)
    private String carNumber;

    @Column(name = "car_color", nullable = false)
    private String carColor;

    @ManyToOne
    @JoinColumn(name ="driver_id", referencedColumnName = "id",nullable = false)
    private Driver driver;

    private Boolean isDeleted;
}
