package app.vercel.ingenio_theta.trakr.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI() {
        final String version = "v1.0";
        final String securitySchemeName = "bearerAuth";

        final SecurityRequirement securityRequirement = new SecurityRequirement()
                .addList(securitySchemeName);

        return new OpenAPI().addSecurityItem(securityRequirement).components(
                new Components().addSecuritySchemes(securitySchemeName, new SecurityScheme()
                        .name(securitySchemeName)
                        .type(Type.HTTP)
                        .scheme("bearer").bearerFormat("JWT")))
                .info(new Info().title("Trakr API").version(version));
    }
}
