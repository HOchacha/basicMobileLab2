package org.mse.basicmobilelab2.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;
import java.util.List;

@Document(collection = "ecgdata")
@Data
@AllArgsConstructor
public class EcgData {
    @Id
    private String id;
    @DBRef
    private User user;
    private Instant dateTime;
    private List<Long> ecgData;
    private int bpm;
    private String bodyStat;
}
