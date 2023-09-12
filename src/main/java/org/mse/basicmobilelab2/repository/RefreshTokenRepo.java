package org.mse.basicmobilelab2.repository;



import org.mse.basicmobilelab2.entity.RefreshToken;
import org.mse.basicmobilelab2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, String> {
    Optional<RefreshToken> findByToken(String token);

    long deleteById(int id);

}
