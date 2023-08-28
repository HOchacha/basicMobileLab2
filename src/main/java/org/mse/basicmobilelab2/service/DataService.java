package org.mse.basicmobilelab2.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.EcgData;
import org.mse.basicmobilelab2.entity.User;
import org.mse.basicmobilelab2.exception.NoJwtException;
import org.mse.basicmobilelab2.payload.request.EcgDataEnrollRequest;
import org.mse.basicmobilelab2.payload.response.MessageResponse;
import org.mse.basicmobilelab2.repository.EcgDataRepo;
import org.mse.basicmobilelab2.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;

@Service
@Log4j2
//의존성 주입 문제가 발생할 경우, 즉시 해당 애너테이션을 제거하고 생성자 주입을 수동으로 작성할 것
public class DataService {
    EcgDataRepo ecgDataRepo;
    JwtUtils jwtUtils;
    UserService userService;
    @Autowired
    public DataService(EcgDataRepo ecgDataRepo, JwtUtils jwtUtils, UserService userService){
        this.userService = userService;
        this.ecgDataRepo = ecgDataRepo;
        this.jwtUtils = jwtUtils;
    }
    public MessageResponse enrollData(EcgDataEnrollRequest ecgDataEnrollRequest){

        String initJwt = ecgDataEnrollRequest.getJwt();
        log.info("JWT : "+initJwt);
        if(initJwt != null && initJwt.startsWith("Bearer ")) {
            String jwt = ecgDataEnrollRequest.getJwt().substring(7, ecgDataEnrollRequest.getJwt().length());
            User user = userService.findUserByUsername(jwtUtils.getUserNameFromJwtToken(jwt));
            log.info(user);
            EcgData ecgData = new EcgData(null, user, Instant.now(), ecgDataEnrollRequest.getEcgData(),ecgDataEnrollRequest.getBpm());
            log.info(ecgData);

                ecgDataRepo.save(ecgData);

        }
        return new MessageResponse("Ok");
    }
}
