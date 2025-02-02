package by.shestakov.ridesservice.repository;

import by.shestakov.ridesservice.entity.Test;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends MongoRepository<Test, String> {
    Test findByName(String name);
}
