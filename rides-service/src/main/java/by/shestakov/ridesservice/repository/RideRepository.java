package by.shestakov.ridesservice.repository;

import by.shestakov.ridesservice.entity.Ride;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RideRepository extends MongoRepository<Ride, String> {

}


