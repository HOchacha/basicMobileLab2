package org.mse.basicmobilelab2.service;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.mse.basicmobilelab2.entity.*;
import org.mse.basicmobilelab2.exception.NoJwtException;
import org.mse.basicmobilelab2.payload.request.EcgDataEnrollRequest;
import org.mse.basicmobilelab2.payload.response.MessageResponse;
import org.mse.basicmobilelab2.repository.BioDataRepository;
import org.mse.basicmobilelab2.repository.EcgDataRepo;
import org.mse.basicmobilelab2.security.jwt.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
//의존성 주입 문제가 발생할 경우, 즉시 해당 애너테이션을 제거하고 생성자 주입을 수동으로 작성할 것
public class DataService {
    EcgDataRepo ecgDataRepo;
    JwtUtils jwtUtils;
    UserService userService;
    BioDataRepository bioDataRepository;
    @Autowired
    public DataService(EcgDataRepo ecgDataRepo, JwtUtils jwtUtils, UserService userService, BioDataRepository bioDataRepository){
        this.userService = userService;
        this.ecgDataRepo = ecgDataRepo;
        this.jwtUtils = jwtUtils;
        this.bioDataRepository = bioDataRepository;
    }
    public ResponseEntity<?> enrollData(EcgDataEnrollRequest ecgDataEnrollRequest){

        String initJwt = ecgDataEnrollRequest.getJwt();
        log.info("JWT : "+initJwt);
        if(initJwt != null && initJwt.startsWith("Bearer ")) {
            String jwt = ecgDataEnrollRequest.getJwt().substring(7, ecgDataEnrollRequest.getJwt().length());
            try {
                User user = userService.findUserByUsername(jwtUtils.getUserNameFromJwtToken(jwt));
                log.info(user);
                EcgData ecgData = new EcgData(null, user, ecgDataEnrollRequest.getDateTime(), ecgDataEnrollRequest.getEcgData(),ecgDataEnrollRequest.getBpm(), setBiologicalStatus(user, ecgDataEnrollRequest.getBpm()));
                log.info(ecgData);
                ecgDataRepo.save(ecgData);
            }  catch (Exception e) {
                return new ResponseEntity<>(new MessageResponse(e.getMessage()),HttpStatus.UNAUTHORIZED);
            }
        }
        return new ResponseEntity(HttpStatus.OK);
    }

    public String setBiologicalStatus(User user, int bpm){
        Optional<BiologicalData> bioData = bioDataRepository.findByUser(user);
        if(bioData.isPresent()) {
            BiologicalData biologicalData = bioData.get();
            ArrayList<Integer> list = getThreshold(biologicalData.getHRrest(),biologicalData.getHRmax(),biologicalData.getHRavailable());
            if(bpm < list.get(0)) {
                return "REST";
            }
            else if(bpm < list.get(1)) {
                return "FAT BURNING";
            }
            else if(bpm < list.get(2)) {
                return "CARDIAC REINFORCE";
            }
            else {
                return "MAX";
            }
        }
        return null;
    }
    private ArrayList<Integer> getThreshold(int HRrest, int HRmax, int HRavailable){
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add((int)0.4*HRavailable + HRrest);
        list.add((int)0.6*HRavailable + HRrest);
        list.add((int)0.85*HRavailable + HRrest);
        return list;
    }
}
