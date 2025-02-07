package by.shestakov.passengerservice.repository;

import by.shestakov.passengerservice.entity.Passenger;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Page<Passenger> findAllByIsDeletedFalse(Pageable pageable);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<Passenger> findByIdAndIsDeletedFalse(Long id);
}