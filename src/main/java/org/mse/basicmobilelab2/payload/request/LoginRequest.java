package org.mse.basicmobilelab2.payload.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema
public class LoginRequest extends io.swagger.v3.oas.models.media.Schema {
    private String username;
    private String password;
}
