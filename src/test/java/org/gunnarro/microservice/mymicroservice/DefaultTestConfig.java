package org.gunnarro.microservice.mymicroservice;

import java.util.UUID;

import org.apache.logging.log4j.ThreadContext;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

/**
 * generic test config for all unit tests 
 */
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test-application.properties")
public abstract class DefaultTestConfig {

    @Autowired
    protected Environment env;

    @BeforeEach
    public void before() {
        ThreadContext.put("UUID", UUID.randomUUID().toString());
    }

    @AfterEach
    public void after() {
        ThreadContext.clearAll();
    }
}