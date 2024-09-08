package com.TrollMarket.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    @Bean
    public OpenAPI setDocumentation(){
        var info = new Info()
                .title("Troll Market")
                .description("Troll Market API Documentation")
                .contact(new Contact().name("Azhar Naufal Putra").email("nputraazhar29@gmail.com"))
                .version("v 0.1.0");
        String bearerAuth  = "bearerAuth";
        var securityRequirement = new SecurityRequirement().addList(bearerAuth);

        var securitySchema = new SecurityScheme()
                .name(bearerAuth)
                .type(SecurityScheme.Type.HTTP)
                .scheme("Bearer")
                .bearerFormat("JWT");
        var securitySchemes = new Components().addSecuritySchemes(bearerAuth, securitySchema);
        return new OpenAPI()
                .info(info)
                .addSecurityItem( securityRequirement)
                .components(securitySchemes);
    };
}
