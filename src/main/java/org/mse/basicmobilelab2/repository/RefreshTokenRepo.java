package org.mse.basicmobilelab2.repository;



import org.mse.basicmobilelab2.entity.RefreshToken;
import org.mse.basicmobilelab2.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepo extends MongoRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);

    long deleteByUser(User user);

}
