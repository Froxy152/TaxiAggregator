package by.shestakov.driverservice.repository;

import by.shestakov.driverservice.entity.Car;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    Page<Car> findAllByIsDeletedFalse(Pageable pageable);

    Optional<Car> findByIdAndIsDeletedFalse(Long id);

    boolean existsByCarNumber(String carNumber);
}
