package com.example.splitbill.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI splitBillOpenAPI() {

        return new OpenAPI()
                .info(new Info()
                        .title("SplitBill REST API")
                        .description("Backend APIs for SplitBill Application built using Spring Boot")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Avishkar Dhonde")
                                .email("avishkardhonde23@gmail.com")));
    }
}
