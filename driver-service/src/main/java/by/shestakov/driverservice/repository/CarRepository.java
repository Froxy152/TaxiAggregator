package by.shestakov.driverservice.repository;

import by.shestakov.driverservice.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car,Long> {
    boolean existsByCarNumber(String carNumber);
}
