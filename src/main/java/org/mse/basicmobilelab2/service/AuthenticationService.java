package org.mse.basicmobilelab2.service;


import jakarta.validation.Valid;
import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.RefreshToken;
import org.mse.basicmobilelab2.entity.User;
import org.mse.basicmobilelab2.payload.request.LoginRequest;
import org.mse.basicmobilelab2.payload.response.JwtResponse;
import org.mse.basicmobilelab2.repository.UserCollectionRepo;
import org.mse.basicmobilelab2.security.jwt.JwtUtils;
import org.mse.basicmobilelab2.security.service.RefreshTokenService;
import org.mse.basicmobilelab2.security.service.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

@Service
@Log4j2
public class AuthenticationService {
    RefreshTokenService refreshTokenService;
    AuthenticationManager authenticationManager;
    UserCollectionRepo userCollectionRepo;

    JwtUtils jwtUtils;
    UserService userService;
    @Autowired
    public AuthenticationService(RefreshTokenService refreshTokenService, AuthenticationManager authenticationManager, UserService userService, JwtUtils jwtUtils, UserCollectionRepo userCollectionRepo) {
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.userService = userService;
        this.userCollectionRepo = userCollectionRepo;
    }


    //동작이 깔끔하지 않음, 혼자서 과다한 작업을 하고 있음
    public JwtResponse setAuthentication(@Valid LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String Jwt = jwtUtils.generateJwtToken(authentication);
        //해당 코드의 존재 이유?
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        RefreshToken refreshToken = refreshTokenService.createRefreshToken();
        User user = userService.findUserByUsername(loginRequest.getUsername());

        if(user.getRefreshToken() != null){
            RefreshToken oldToken = user.getRefreshToken();
            user.setRefreshToken(refreshToken);
            userCollectionRepo.save(user);
            refreshTokenService.deleteRefreshToken(oldToken);
        }
        return new JwtResponse(Jwt, refreshToken.getToken(), user.getUsername(), user.getEmail());
    }
}
