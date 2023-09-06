package org.mse.basicmobilelab2.repository;

import lombok.Data;
import org.mse.basicmobilelab2.entity.BiologicalData;
import org.mse.basicmobilelab2.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BioDataRepository extends MongoRepository<BiologicalData, User> {
    Optional<BiologicalData> findByUser(User user);
}
