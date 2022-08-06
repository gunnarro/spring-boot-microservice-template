package org.gunnarro.microservice.mymicroservice.config;

iimport org.springframework.context.annotation.Bean;
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

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests(authz -> authz
                        .antMatchers("/api-docs", "/swagger-resources/**", "/swagger-ui.html", "/webjars/**", "/actuator/**")
                        .permitAll()
                        .anyRequest().authenticated()
                )
                .httpBasic();
        return http.build();
    }
}
