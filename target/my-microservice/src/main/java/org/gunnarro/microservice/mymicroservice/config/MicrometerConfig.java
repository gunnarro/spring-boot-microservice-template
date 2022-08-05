package org.gunnarro.microservice.mymicroservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;

@Configuration
public class MicrometerConfig {

    @Bean
    public MeterRegistryCustomizer<MeterRegistry> metricsCommonTags(@Autowired BuildProperties buildProperties) {
        return registry -> registry.config()
                .commonTags("application", buildProperties.getName())
                .commonTags("application_version", buildProperties.getVersion())
                .commonTags("application_build_time", buildProperties.getTime().toString())
                .meterFilter(MeterFilter.deny(id -> {
                    String uri = id.getTag("uri");
                    return uri != null && uri.startsWith("/swagger");
                }));
    }
}