package org.mse.basicmobilelab2.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "roles")
@Setter
@Getter
@Schema(description = "특수 인증 수단 없이는 관리자 권한 부여 불가, null 값으로 둘 경우 자동으로 User 권한 부여")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer oid;
    @Column(length = 30)
    private String rolename;
}