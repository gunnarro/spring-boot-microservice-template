package org.gunnarro.microservice.mymicroservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(
        info = @Info(
                title = "Spring Boot Microservice",
                description = "Spring Boot Microservice Rest Service Template",
                version = "V1",
                contact = @Contact(
                        name = "maintainer@organization.org",
                        url = "https://my-orgainzation.org",
                        email = "maintainer@organization.com"
                )),
        servers = @Server(url = "https://localhost:9998")
)
@SecurityScheme(
        name = "api",
        scheme = "basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)

public class OpenAPIConfig {
}
