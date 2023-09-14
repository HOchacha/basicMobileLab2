package org.mse.basicmobilelab2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import io.hypersistence.utils.hibernate.type.json.JsonType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Type;


import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "ecgData")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "1회 측정한 ecgData 정보")
public class EcgData {
    @Id()
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "user_username")
    private User user;

    @Column
    private LocalDateTime dateTime;

    @Column(columnDefinition = "longtext")
    @Type(JsonType.class)
    private Map<String, List<Long>> ecgData;

    @Column
    private int bpm;

    @Column(length = 30)
    private String bodyStat;
    public EcgData(User user, LocalDateTime localDateTime, Map<String, List<Long>> ecgData, int bpm, String bodyStat){
        this.user = user;
        this.bodyStat =bodyStat;
        this.user = user;
        this.dateTime = localDateTime;
        this.ecgData = ecgData;
        this.bpm = bpm;
        this.bodyStat = bodyStat;
    }


}
