package org.mse.basicmobilelab2.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Log4j2
@Schema
public class EcgDataEnrollRequest {
    private LocalDateTime dateTime;
    private String jwt;
    private List<Long> ecgData;
    private int bpm;

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getJwt() {
        log.info(jwt);
        return jwt;
    }

    public List<Long> getEcgData() {
        return ecgData;
    }

    public int getBpm() {
        return bpm;
    }
}
