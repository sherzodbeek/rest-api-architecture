package com.epam;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
        info=@Info(title="Subscriptions",
        description = "User and Subscription Service",
        version = "1.0.0",
        contact = @Contact(
                name = "Sherzod Omonov",
                email = "sherzod_omonov@epam.com"
        )))
public class RestApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestApplication.class, args);
    }
}
