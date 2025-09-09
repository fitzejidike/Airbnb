package com.example.airbnb.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI airbnbOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("🏡 Airbnb-like Property Management API")
                        .description("A modular backend system for managing properties, bookings, users, and payments with Paystack integration.")
                        .version("v1.0.0")
                        .contact(new Contact()
                                .name("Fitzgerald Ejidike")
                                .url("https://github.com/fitzejidike")
                                .email("fitzdev25@gmail.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")))
                .externalDocs(new ExternalDocumentation()
                        .description("Project Repository")
                        .url("https://github.com/fitzejidike/airbnb-clone"));
    }
}
