package org.mse.basicmobilelab2.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.mse.basicmobilelab2.entity.EcgData;
import org.mse.basicmobilelab2.payload.request.EcgDataEnrollRequest;
import org.mse.basicmobilelab2.payload.response.MessageResponse;
import org.mse.basicmobilelab2.service.DataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.crypto.Data;

@RestController
@RequestMapping("/api/history")
@CrossOrigin(origins = "*", maxAge = 3600)
@Log4j2
public class DataController {
    DataService dataService;

    @Autowired
    public DataController(DataService dataService){
        this.dataService = dataService;
    }

    @PostMapping("/data")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> enlistEcgData(@RequestBody EcgDataEnrollRequest ecgDataEnrollRequest){
        log.info(ecgDataEnrollRequest);
        log.info(ecgDataEnrollRequest.getDateTime());
        log.info(ecgDataEnrollRequest.getJwt());
        log.info(ecgDataEnrollRequest.getBpm());
        log.info(ecgDataEnrollRequest.getEcgData());
        return dataService.enrollData(ecgDataEnrollRequest);
    }

}
