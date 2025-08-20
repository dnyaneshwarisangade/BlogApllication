package com.blog.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        
        return new OpenAPI().addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
                .components(new Components().addSecuritySchemes("bearerAuth",
                        new SecurityScheme()
                                .name("bearerAuth")
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT")
                ))
        		.info(new Info()
        		.title("Blogging Apllication")
        		.version("1.0")
        		.description("This project is developed By DS")
        		.termsOfService("\"https://example.com/terms\"")
        		.contact(new Contact()
        				.name("Dnyaneshwari Sangade").url("https://example.com").email("dnyanoffice@gmail.com"))
        		.license(new License().name("Apache 2.0")
                        .url("https://springdoc.org")));
    }
}