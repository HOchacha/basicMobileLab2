package org.mse.basicmobilelab2.controller;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.RefreshToken;
import org.mse.basicmobilelab2.entity.User;
import org.mse.basicmobilelab2.exception.TokenRefreshException;
import org.mse.basicmobilelab2.exception.UserEmailDuplicationException;
import org.mse.basicmobilelab2.exception.UsernameDuplicatedException;
import org.mse.basicmobilelab2.payload.request.LoginRequest;
import org.mse.basicmobilelab2.payload.request.SignupRequest;
import org.mse.basicmobilelab2.payload.request.TokenRefreshRequest;
import org.mse.basicmobilelab2.payload.response.JwtResponse;
import org.mse.basicmobilelab2.payload.response.MessageResponse;
import org.mse.basicmobilelab2.payload.response.TokenRefreshResponse;
import org.mse.basicmobilelab2.security.jwt.JwtUtils;
import org.mse.basicmobilelab2.security.service.RefreshTokenService;
import org.mse.basicmobilelab2.service.AuthenticationService;
import org.mse.basicmobilelab2.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Log4j2
@Controller
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/auth")
@OpenAPIDefinition(info = @Info(title = "Login Authorization", description = "Sign I/O & up", version = "v1"))
public class AuthController {
    RefreshTokenService refreshTokenService;
    JwtUtils jwtUtils;
    AuthenticationService authenticationService;
    UserService userService;

    @Autowired
    public AuthController(UserService userService, RefreshTokenService refreshTokenService, AuthenticationService authenticationService, JwtUtils jwtUtils) {
        this.refreshTokenService = refreshTokenService;
        this.jwtUtils = jwtUtils;
        this.authenticationService = authenticationService;
        this.userService = userService;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(
                authenticationService.setAuthentication(loginRequest)
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        log.info(requestRefreshToken);

        Optional<RefreshToken> optionalRefreshToken = refreshTokenService.findByToken(requestRefreshToken);
        if (!optionalRefreshToken.isPresent()) {
            throw new TokenRefreshException(requestRefreshToken, "Refresh token is not in database!");
        }
        RefreshToken validRefreshToken = refreshTokenService.verifyExpiration(optionalRefreshToken.get());
        log.info(validRefreshToken);
        User user = validRefreshToken.getUser();
        log.info(user);
        String token = jwtUtils.generateTokenFromUsername(user.getUsername());
        log.info(token);

        return ResponseEntity.ok(new TokenRefreshResponse(token, requestRefreshToken));
        //jwtUtils를 어떻게 빼야할지 고민, refreshTokenService 쪽으로 빼고 싶지만, map 함수의 구조가 이해되지 않음
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        log.info(signUpRequest);
        try {
            User user = userService.enrollUser(signUpRequest);
            log.info(user);
            JwtResponse jwtResponse = authenticationService.setAuthentication(new LoginRequest(signUpRequest.getUsername(), signUpRequest.getPassword()));

            return ResponseEntity.ok(jwtResponse);
        }
        catch(UsernameDuplicatedException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error : Username is already in use."));
        }
        catch(UserEmailDuplicationException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error : Email is already using."));
        }
    }
}
