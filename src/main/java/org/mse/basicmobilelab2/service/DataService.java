package org.mse.basicmobilelab2.service;

import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.*;
import org.mse.basicmobilelab2.exception.NoInstanceException;
import org.mse.basicmobilelab2.payload.request.EcgDataEnrollRequest;
import org.mse.basicmobilelab2.payload.response.MessageResponse;
import org.mse.basicmobilelab2.repository.EcgDataRepo;
import org.mse.basicmobilelab2.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public ResponseEntity<?> enrollData(EcgDataEnrollRequest ecgDataEnrollRequest){

        String initJwt = ecgDataEnrollRequest.getJwt();
        log.info("JWT : "+initJwt);
        if(initJwt != null && initJwt.startsWith("Bearer ")) {
            String jwt = ecgDataEnrollRequest.getJwt().substring(7, ecgDataEnrollRequest.getJwt().length());
            try {
                User user = userService.findUserByUsername(jwtUtils.getUserNameFromJwtToken(jwt));
                log.info(user);
                EcgData ecgData = new EcgData( user, ecgDataEnrollRequest.getDateTime(), toJSON(ecgDataEnrollRequest.getEcgData()),ecgDataEnrollRequest.getBpm(), "good");
                log.info(ecgData);
                ecgDataRepo.save(ecgData);
            }  catch (Exception e) {
                return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }
    private Map<String, List<Long>> toJSON(List<Long> jsonList){
        Map<String, List<Long>> data = new HashMap<>();
        data.put("ecgdata", jsonList);

        return data;
    }
    private ArrayList<Integer> getThreshold(int HRrest, int HRavailable){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add((int)0.4*HRavailable + HRrest);
        list.add((int)0.6*HRavailable + HRrest);
        list.add((int)0.85*HRavailable + HRrest);
        return list;
    }

    public List<EcgData> getECGhistory(String initJwt) {

        if(initJwt != null && initJwt.startsWith("Bearer ")) {
            String jwt = initJwt.substring(7, initJwt.length());
            User user = userService.findUserByUsername(jwtUtils.getUserNameFromJwtToken(jwt));
            return ecgDataRepo.findByUser(user);
        } else {
            throw new NoInstanceException("No Data");
        }
    }
}
