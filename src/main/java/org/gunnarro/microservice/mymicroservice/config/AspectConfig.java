package org.gunnarro.microservice.mymicroservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * In order to enable aspect only. Needed because of @see RequestLoggingConfig
 */
@Configuration
@EnableAspectJAutoProxy
public class AspectConfig {
}