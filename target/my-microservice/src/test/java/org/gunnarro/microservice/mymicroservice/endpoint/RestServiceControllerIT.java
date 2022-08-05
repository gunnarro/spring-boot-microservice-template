package org.gunnarro.microservice.mymicroservice.endpoint;

import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.actuate.metrics.AutoConfigureMetrics;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

/**
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "/test-application.properties")
@AutoConfigureMetrics
class RestServiceControllerIT {

    // @LocalServerPort
    @Value("${server.port}")
    private int port;
    private RestTemplate testRestTemplate;
    private HttpHeaders requestHeaders;

    @Value("${spring.security.user.name}")
    private String username;
    @Value("${spring.security.user.password}")
    private String password;

    @BeforeEach
    public void init() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
        // NoopHostnameVerifier essentially turns hostname verification off
        CloseableHttpClient httpClient = HttpClientBuilder.create().setSSLHostnameVerifier(new NoopHostnameVerifier()).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        testRestTemplate = new RestTemplate(requestFactory);
        testRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        requestHeaders = createHeaders(username, password);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
    }

    @Test
    void restServiceIsAlive() {
        HttpEntity<Void> entity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<Void> response = testRestTemplate.exchange(createURLWithPort("https","restservice/v1/isalive"), HttpMethod.GET, entity, Void.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void swaggerIsAlive() {
        HttpEntity<String> entity = new HttpEntity<>(null, requestHeaders);
        ResponseEntity<String> response = testRestTemplate.exchange(createURLWithPort("https","swagger-ui.html"), HttpMethod.GET, entity, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void actuatorIsAlive() {
        port = 9998;
        ResponseEntity<Object> response = testRestTemplate.exchange(createURLWithPort("http","actuator"), HttpMethod.GET, null, Object.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
    
    @Test
    void prometheusIsAlive() {
        port = 9998;
        ResponseEntity<String> response = testRestTemplate.exchange(createURLWithPort("http","actuator/prometheus"), HttpMethod.GET, null, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    private String createURLWithPort(String protocol, String uri) {
        return String.format("%s://localhost:%s/%s",protocol, port, uri);
    }

    private HttpHeaders createHeaders(String username, String password) {
        return new HttpHeaders() {
            private static final long serialVersionUID = 1L;
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("UTF8")));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }

}