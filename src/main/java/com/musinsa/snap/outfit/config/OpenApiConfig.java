package com.musinsa.snap.outfit.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        Info info = new Info()
            .title("Snap Outfit Service API DOCS")
            .version("v1")
            .description("스냅 코디 서비스 api 명세서");

        return new OpenAPI()
            .info(info);
    }
}
