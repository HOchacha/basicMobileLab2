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
import java.time.LocalDateTime;
import java.time.ZoneId;
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

    public RefreshToken createRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();


        Instant time = Instant.now().plusMillis(refreshTokenDurationMs);
        LocalDateTime localDateTime = time.atZone(ZoneId.systemDefault()).toLocalDateTime();

        refreshToken.setExpiryDate(localDateTime);
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepo.save(refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        User user = userCollectionRepo.findByRefreshToken(token).get();
        if (token.getExpiryDate().atZone(ZoneId.systemDefault()).toInstant().compareTo(Instant.now()) < 0) {
            user.setRefreshToken(null);
            refreshTokenRepo.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }
        return token;
    }

    @Transactional
    public void deleteRefreshToken(RefreshToken refreshToken) {
        refreshTokenRepo.delete(refreshToken);
    }


}
