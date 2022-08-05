package org.gunnarro.microservice.mymicroservice.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;

@ContextConfiguration(classes = SwaggerConfig.class)
class SwaggerConfigTest extends DefaultTestConfig {

    @Autowired
    SwaggerConfig swaggerConfig;

    @Test
    void swaggerConfigOk() {
        Assertions.assertNotNull(swaggerConfig.serviceApiv10());
    }
}
