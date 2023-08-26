package org.mse.basicmobilelab2.security.service;


import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.RefreshToken;
import org.mse.basicmobilelab2.entity.User;
import org.mse.basicmobilelab2.exception.TokenRefreshException;
import org.mse.basicmobilelab2.repository.RefreshTokenRepo;
import org.mse.basicmobilelab2.repository.UserCollectionRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Log4j2
public class RefreshTokenService {
    @Value("${refreshTokenExpirationMs}")
    private Long refreshTokenDurationMs;

    @Autowired
    public RefreshTokenService(RefreshTokenRepo refreshTokenRepo, UserCollectionRepo userCollectionRepo) {
        this.refreshTokenRepo = refreshTokenRepo;
        this.userCollectionRepo = userCollectionRepo;
    }
    private RefreshTokenRepo refreshTokenRepo;
    private UserCollectionRepo userCollectionRepo;

    public Optional<RefreshToken> findByToken(String token){
        log.info(token);
        return refreshTokenRepo.findByToken(token);
    }
    public RefreshToken createRefreshToken(String userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userCollectionRepo.findById(userId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public int deleteByUserId(String userId) {
        User user = userCollectionRepo.findById(userId).get();
        //deleteByUser -> User 객체 필요
        return (int)refreshTokenRepo.deleteByUser(user);
    }


}
