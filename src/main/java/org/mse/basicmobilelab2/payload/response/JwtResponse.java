package org.mse.basicmobilelab2.payload.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class JwtResponse {
    private String token;
    private String type = "Bearer";
    private String refreshToken;
    private String username;
    private String email;

    public JwtResponse(String accessToken, String refreshToken,  String username, String email) {
        this.token = accessToken;
        this.refreshToken = refreshToken;
        this.username = username;
        this.email = email;
    }
}
