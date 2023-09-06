package org.mse.basicmobilelab2.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.time.Instant;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Log4j2
public class EcgDataEnrollRequest {
    private Instant dateTime;
    private String jwt;
    private List<Long> ecgData;
    private int bpm;

    public Instant getDateTime() {
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
