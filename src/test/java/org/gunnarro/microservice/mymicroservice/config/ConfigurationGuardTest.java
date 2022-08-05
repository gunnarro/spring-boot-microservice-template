package org.gunnarro.microservice.mymicroservice.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.test.context.ContextConfiguration;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;

@ContextConfiguration(classes = { ConfigurationGuard.class, BuildProperties.class })
class ConfigurationGuardTest extends DefaultTestConfig {

    @Autowired
    ConfigurationGuard configurationGuard;
    
    @Test
    void configurationGuardOk() {
        System.setProperty("SERVER_IDENTITY_KEYSTORE_PATH", "/micorservice/jks");
        System.setProperty("SERVER_IDENTITY_KEYSTORE_ALIAS", "domain");
        System.setProperty("SERVER_IDENTITY_KEYSTORE_PASS", "change-me");
        Assertions.assertNotNull(configurationGuard);
    }
}