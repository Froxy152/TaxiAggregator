package by.shestakov.driverservice.repository;

import by.shestakov.driverservice.entity.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
}
