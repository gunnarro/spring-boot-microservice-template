package org.gunnarro.microservice.mymicroservice.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

/**
 * Check and throw an exception if required application properties are missing
 * during application startup. For example, you are not allowed to run an
 * application without ssl enabled
 *
 * Note! Some system environment varaibles must be set on local PC
 */
@Configuration
@Slf4j
public class ConfigurationGuard implements InitializingBean {
    @Value("${server.ssl.enabled}")
    private boolean sslEnabled;

    @Value("${server.ssl.protocol}")
    private String sslProtocol;

    @Value("${management.endpoint.prometheus.enabled}")
    private boolean prometheusEnabled;

    public void afterPropertiesSet() {
        if (!sslEnabled) {
            log.error("Application startup rejected: application property 'server.ssl.enabled' must be enabled!");
            throw new IllegalArgumentException("Application startup rejected: application property 'server.ssl.enabled' must be enabled!");
        }

        if (this.sslProtocol == null || !this.sslProtocol.equals("TLS")) {
            log.error("Application startup rejected: application property 'server.ssl.protocol' must be equal TLS!");
            throw new IllegalArgumentException("Application startup rejected: application property 'server.ssl.protocol' must be equal TLS!");
        }

        if (!prometheusEnabled) {
            log.error("Application startup rejected: application property 'management.endpoint.prometheus.enabled' must be enabled!");
            throw new IllegalArgumentException("Application startup rejected: application property 'management.endpoint.prometheus.enabled' must be enabled!");
        }

        // Note! should start using a vault, for example spring vault
        // check for required environment variables
        if (ObjectUtils.isEmpty(System.getProperty("SERVER_IDENTITY_KEYSTORE_PATH"))) {
            log.error("Application startup rejected: system environment variable 'SERVER_IDENTITY_KEYSTORE_PATH' must be set!");
            throw new IllegalArgumentException("Application startup rejected: system environment variable 'SERVER_IDENTITY_KEYSTORE_PATH' must be set!");
        }

        if (ObjectUtils.isEmpty(System.getProperty("SERVER_IDENTITY_KEYSTORE_PASS"))) {
            log.error("Application startup rejected: system environment variable 'SERVER_IDENTITY_KEYSTORE_PASS' must be set!");
            throw new IllegalArgumentException("Application startup rejected: system environment variable 'SERVER_IDENTITY_KEYSTORE_PASS' must be set!");
        }

        if (ObjectUtils.isEmpty(System.getProperty("SERVER_IDENTITY_KEYSTORE_ALIAS"))) {
            log.error("Application startup rejected: system environment variable 'SERVER_IDENTITY_KEYSTORE_ALIAS' must be set!");
            throw new IllegalArgumentException("Application startup rejected: system environment variable 'SERVER_IDENTITY_KEYSTORE_ALIAS' must be set!");
        }
    }
}
