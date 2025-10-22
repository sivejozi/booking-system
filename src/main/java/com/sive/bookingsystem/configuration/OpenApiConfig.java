/*
package com.sive.bookingsystem.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI myOpenAPI() {
        Server local = new Server();
        local.setUrl("https://localhost:8443");
        local.setDescription("Server URL in local environment");

        Server devServer = new Server();
        devServer.setUrl("https://devapi.citypower.co.za");
        devServer.setDescription("Server URL in Development environment");

        Server testServer = new Server();
        testServer.setUrl("https://uatapi.citypower.co.za");
        testServer.setDescription("Server URL in Test environment");

        Server prodServer = new Server();
        prodServer.setUrl("https://api.citypower.co.za");
        prodServer.setDescription("Server URL in Production environment");
        Info info = new Info().title("CityPower Management API").version("1.0").description("This API exposes endpoints to manage CityPower website/customer portal.");

        return new OpenAPI().info(info).servers(List.of(local, devServer, testServer, prodServer)).addSecurityItem(new SecurityRequirement().addList("Bearer Authentication")).components(new Components().addSecuritySchemes("Bearer Authentication", createAPIKeyScheme()));
    }

    private SecurityScheme createAPIKeyScheme() {
        return new SecurityScheme().type(SecurityScheme.Type.HTTP).bearerFormat("JWT").scheme("bearer");
    }
}*/
