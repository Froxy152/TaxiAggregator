package by.shestakov.passengerservice.repository;

import by.shestakov.passengerservice.entity.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
}
