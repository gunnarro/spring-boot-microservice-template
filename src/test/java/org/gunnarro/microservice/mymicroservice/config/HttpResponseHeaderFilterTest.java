package org.gunnarro.microservice.mymicroservice.config;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;

@ContextConfiguration(classes = { HttpResponseHeaderFilter.class })
class HttpResponseHeaderFilterTest extends DefaultTestConfig {

    @Autowired
    HttpResponseHeaderFilter httpResponseHeaderFilter;

    @Mock
    HttpServletRequest request;

    @Mock
    HttpServletResponse response;

    @Mock
    FilterChain filterChain;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void httpResponseHeaderFilterOk() {
        Assertions.assertNotNull(httpResponseHeaderFilter);
    }

    @Test
    void doFilterNoUUID() {
        assertDoesNotThrow(() -> httpResponseHeaderFilter.doFilter(request, response, filterChain));
        assertDoesNotThrow(() -> httpResponseHeaderFilter.destroy());
    }
}
