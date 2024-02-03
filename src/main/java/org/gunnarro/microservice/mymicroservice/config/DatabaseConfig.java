package org.gunnarro.microservice.mymicroservice.config;

import jakarta.sql.DataSource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@Slf4j
public class DatabaseConfig {
    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.microservice")
    public DataSourceProperties microserviceDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean(name = "microserviceDataSource")
    @Primary
    @ConfigurationProperties("spring.datasource.microservice.configuration")
    public DataSource microserviceDataSource() {
        return microserviceDataSourceProperties()
                .initializeDataSourceBuilder()
                .type(HikariDataSource.class)
                .build();
    }

    @Bean(name = "microserviceJdbcTemplate")
    public JdbcTemplate microserviceJdbcTemplate(@Qualifier("microserviceDataSource") DataSource microserviceDataSource) {
        log.debug("microserviceJdbcTemplate dataSource:{}", microserviceDataSource);
        return new JdbcTemplate(microserviceDataSource);
    }

}
