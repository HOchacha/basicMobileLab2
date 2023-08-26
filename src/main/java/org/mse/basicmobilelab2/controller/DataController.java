package org.mse.basicmobilelab2.controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.mse.basicmobilelab2.entity.EcgData;
import org.mse.basicmobilelab2.payload.response.MessageResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.xml.crypto.Data;

@RestController
@RequestMapping("/api/test")
@CrossOrigin(origins = "*", maxAge = 3600)
@OpenAPIDefinition(info = @Info(title = "Authentication Test", description = "", version = "v1"))
public class DataController {
    @PostMapping
    public ResponseEntity<?> enlistEcgData(EcgData ecgData){
        return ResponseEntity.ok(new MessageResponse("OK"));
    }
}
