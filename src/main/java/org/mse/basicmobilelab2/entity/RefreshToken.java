package org.mse.basicmobilelab2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@Schema(description = "JWT 재발급 수단")
public class RefreshToken {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 50)
    private String token;
    @Column
    private LocalDateTime expiryDate;

    @OneToOne(mappedBy = "refreshToken")
    private User user;
}
