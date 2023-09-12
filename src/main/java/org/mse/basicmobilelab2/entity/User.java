package org.mse.basicmobilelab2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "user")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "회원 정보")
public class User {
    public User(String username, String password, String email, String name, String gender, String exerciseStat, int age){
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.gender = gender;
        this.excerciseStat = exerciseStat;
        this.age = age;
    }

    @Id
    @Column(length = 50, nullable = false)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 40)
    private String name;

    @Column(length = 40)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    @Column
    private Integer age;

    @Column(length = 1)
    @NotNull
    private String gender;
    //use abbreviation, male for M either female for F

    @Column
    private String excerciseStat;

    @Column
    private Integer HRrest;

    @Column
    private Integer HRmax;

    @Column
    private Integer HRavailable;

    @JoinColumn(name="refreshToken_id")
    @OneToOne
    private RefreshToken refreshToken;
}