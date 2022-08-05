package org.gunnarro.microservice.mymicroservice.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;

@WebMvcTest
@ContextConfiguration(classes = SecurityConfig.class) 
class SecurityConfigTest extends DefaultTestConfig {

    @Autowired
    SecurityConfig securityConfig;

    @Test
    void securityConfigOk() {
        Assertions.assertNotNull(securityConfig);
    }
}
