package org.gunnarro.microservice.mymicroservice.config;

import io.micrometer.core.aop.TimedAspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.metrics.MeterRegistryCustomizer;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.config.MeterFilter;

/**
 * FIXME build properties is not working, therefore disabled, this is a minor task.
 *
 * In a Spring Boot project, we add the build-info goal to the spring-boot-maven-plugin within the project’s pom.xml file:
 * BuildProperties needs the <id>build-info</id> in the <artifactId>spring-boot-maven-plugin</artifactId>, ref pom.xml
 *
 * The application version is used in grafana dashbord to display current deployed version.
 * This is nice to have and particular when many instances is running of an application.
 */
//@Configuration
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

    /**
     * Applying TimedAspect makes @Timed usable on any arbitrary method in an
     * AspectJ proxied instance.
     */
    @Bean
    public TimedAspect timedAspect(MeterRegistry registry) {
        return new TimedAspect(registry);
    }
}