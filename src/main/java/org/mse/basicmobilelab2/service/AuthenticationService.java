package org.mse.basicmobilelab2.service;


import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.RefreshToken;
import org.mse.basicmobilelab2.entity.User;
import org.mse.basicmobilelab2.payload.request.LoginRequest;
import org.mse.basicmobilelab2.payload.response.JwtResponse;
import org.mse.basicmobilelab2.security.jwt.JwtUtils;
import org.mse.basicmobilelab2.security.service.RefreshTokenService;
import org.mse.basicmobilelab2.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class AuthenticationService {
    RefreshTokenService refreshTokenService;
    AuthenticationManager authenticationManager;

    JwtUtils jwtUtils;
    UserService userService;
    @Autowired
    public AuthenticationService(RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager, UserService userService, JwtUtils jwtUtils) {
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    public JwtResponse setAuthentication(@Valid LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String Jwt = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        //굳이 authentication의 Principal로 값을 받아와야 하나 궁금함
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getId());
        //단일 역할 위반 가능성, UserService 로 이임하는 것이 좋을 수 있음
        User user = userService.findUserByUsername(loginRequest.getUsername());
        return new JwtResponse(Jwt, refreshToken.getToken(),user.getId(), user.getUsername(), user.getEmail(),user.getSchoolName());
    }
}
