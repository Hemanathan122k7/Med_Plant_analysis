package com.medicinal.plant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger/OpenAPI 3 Configuration
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI medicinalPlantOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Medicinal Plant Identifier API")
                        .description("REST API for Medicinal Plant Identification and Information System")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Medicinal Plant Team")
                                .email("support@medicinalplant.com")
                                .url("https://medicinalplant.com"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components()
                        .addSecuritySchemes("bearerAuth",
                                new SecurityScheme()
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Enter JWT token")));
    }
}
