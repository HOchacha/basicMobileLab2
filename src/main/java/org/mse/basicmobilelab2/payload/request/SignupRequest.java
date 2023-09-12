package org.mse.basicmobilelab2.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String username;
    private String email;
    private String password;
    private String name;
    private Set<String> roles;
    private String gender;
    private String exerciseStat;
    private int age;
}
