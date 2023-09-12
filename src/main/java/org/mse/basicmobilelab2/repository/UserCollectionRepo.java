package org.mse.basicmobilelab2.repository;

import org.mse.basicmobilelab2.entity.RefreshToken;
import org.mse.basicmobilelab2.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCollectionRepo extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    Optional<User> findById(String id);
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<User> findByRefreshToken(RefreshToken refreshToken);
}
