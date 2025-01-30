package by.shestakov.passengerservice.repository;

import by.shestakov.passengerservice.entity.Passenger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PassengerRepository extends JpaRepository<Passenger, Long> {
    Page<Passenger> findALlByIsDeletedFalse(Pageable pageable);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);

    Optional<Passenger> findByIdAndIsDeletedFalse(Long id);
}
