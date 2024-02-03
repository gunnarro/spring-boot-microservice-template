package org.gunnarro.microservice.mymicroservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;


/**
 * Security configuration for Rest API and endpoints
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * CORS enables cross-domain communication, is turned off as default.
     * If needed, activate cors on controller or method level with @CrossOrigin annotation.
     * Do not turn if off here, at global level.
     * <p>
     * CSRF ensure that it is safe before disabled. see <a href="https://www.baeldung.com/csrf-stateless-rest-api">csrf-stateless-rest-api</a>
     * <p>
     * Both CORS and CSRF are activated as default bt Spring Security 4.x and higher.
     */
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (requests) -> requests
                                .requestMatchers(HttpMethod.GET, "/api-docs/*", "/swagger-resources/**", "/swagger-ui.html", "/actuator/**").permitAll()
                                .anyRequest().authenticated()
                );
        return http.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("/**"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PATCH", "PUT", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Requestor-Type"));
        configuration.setExposedHeaders(List.of("X-Get-Header"));
        configuration.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
