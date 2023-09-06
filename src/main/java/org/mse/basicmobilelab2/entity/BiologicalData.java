package org.mse.basicmobilelab2.entity;

import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Document(collection = "biologicaldata")
@Data
@AllArgsConstructor

public class BiologicalData {
    @DBRef
    @Id
    private User user;
    private int age;
    @DBRef
    private EexceciseStat exerciseStat;
    //신체 능력에 관한 데이터
    private int restBpm;
    private int HRrest;
    private int HRavailable;
    private int HRmax;
    private ArrayList<Integer> HRSectionThreshold;
    private String STATUS;
    private LocalDateTime localDateTime;
    public BiologicalData(User user, int age, EexceciseStat exerciseStat, int restBpm, int HRrest) {
        this.user = user;
        this.age = age;
        this.exerciseStat = exerciseStat;
        this.restBpm = restBpm;
        this.HRrest = HRrest;
        this.HRavailable = HRmax - HRrest;
        this.HRmax = 220 - age;
    }
}
