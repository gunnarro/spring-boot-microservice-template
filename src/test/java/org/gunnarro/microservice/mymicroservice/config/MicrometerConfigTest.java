package org.gunnarro.microservice.mymicroservice.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.test.context.ContextConfiguration;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;

@ContextConfiguration(classes = { MicrometerConfig.class, BuildProperties.class })
class MicrometerConfigTest extends DefaultTestConfig {

    @Autowired
    MicrometerConfig micrometerConfig;

    @Test
    void micrometerConfigOk() {
        Assertions.assertNotNull(micrometerConfig);
    }

}
