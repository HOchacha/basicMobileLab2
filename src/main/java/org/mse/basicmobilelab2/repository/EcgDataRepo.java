package org.mse.basicmobilelab2.repository;

import org.mse.basicmobilelab2.entity.EcgData;
import org.mse.basicmobilelab2.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EcgDataRepo extends MongoRepository<EcgData, Long> {
    Optional<EcgData> findByUser(User user);
}
