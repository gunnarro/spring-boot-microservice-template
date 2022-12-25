package org.gunnarro.microservice.mymicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


/**
 * Security configuration for Rest API and endpoints
 * 
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
public class SecurityConfig {

    /**
     * CORS enables cross-domain communication, is turned off as default.
     * If needed, activate cors on controller or method level with @CrossOrigin annotaion.
     * Do not turn if off here, at global level.
     *
     * CSRF ensure that is is safe before disabled. see https://www.baeldung.com/csrf-stateless-rest-api,
     *
     * Both CORS and CSRF are activated as default bt Spring Security 4.x and higher.
     */
    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(authz -> authz
                        .antMatchers("/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/actuator/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic();
        return http.build();
    }
}
