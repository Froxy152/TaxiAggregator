package by.shestakov.driverservice.repository;

import by.shestakov.driverservice.entity.Driver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver, Long> {
    Page<Driver> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Driver> findByIdAndIsDeletedFalse(Long id);

    boolean existsByEmailOrPhoneNumber(String email, String phoneNumber);
}
