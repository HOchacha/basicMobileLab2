package org.mse.basicmobilelab2.config;


import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.mse.basicmobilelab2.security.jwt.AuthTokenFilter;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
@Configuration
@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(title = "Login Authorization", description = "Sign I/O & up", version = "v1"))
@SecuritySchemes({
        @SecurityScheme(
                name = "JWT-Auth",
                type = SecuritySchemeType.APIKEY,
                description = "Api token",
                in = SecuritySchemeIn.HEADER,
                paramName = "Authorization"
        )
})
public class SwaggerConfig {

    @Bean
    public OpenAPI OpenApi(){
        return new OpenAPI().info(new Info());
    }
}
