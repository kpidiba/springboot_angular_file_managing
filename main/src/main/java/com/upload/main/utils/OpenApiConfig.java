package com.upload.main.utils;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "FILE MANAGEMENT", version = "1.0.0", license = @License(name = "Apache 2.0", url = "https://www.apache.org/licenses/LICENSE-2.0"), description = "File Management Project With Spring boot and Angular", contact = @Contact(name = "KBrightCoder", email = "kpidibadavid1@gmail.com", url = "https://davidkbright.com")), servers = {
        @Server(description = "Local Server", url = "http://localhost:8080"),
        @Server(description = "Production Server", url = "https://davidkbright.com")
})
@SecurityScheme(
    name="bearerAuth",
    description = "JWT auth in the form of Bearer token",
    scheme = "bearer",
    bearerFormat = "JWT",
    type = SecuritySchemeType.HTTP,
    in = SecuritySchemeIn.HEADER
)
public class OpenApiConfig {

}
