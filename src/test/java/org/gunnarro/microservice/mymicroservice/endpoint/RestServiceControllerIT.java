package org.gunnarro.microservice.mymicroservice.endpoint;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.BasicHttpClientConnectionManager;
import org.apache.hc.client5.http.socket.ConnectionSocketFactory;
import org.apache.hc.client5.http.socket.PlainConnectionSocketFactory;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.utils.Base64;
import org.apache.hc.core5.http.config.Registry;
import org.apache.hc.core5.http.config.RegistryBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.actuate.observability.AutoConfigureObservability;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.Serial;
import java.nio.charset.StandardCharsets;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

/**
 *
 */
@Disabled("must fix property encryption")
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@TestPropertySource(locations = "file:src/test/resources/test-application.properties")
@AutoConfigureObservability
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
    public void init() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException, IOException {
        final TrustStrategy acceptingTrustStrategy = (cert, authType) -> true;
        final SSLContext sslContext = SSLContexts.custom()
                .loadTrustMaterial(null, acceptingTrustStrategy)
                .build();
        final SSLConnectionSocketFactory sslsf =
                new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        final Registry<ConnectionSocketFactory> socketFactoryRegistry =
                RegistryBuilder.<ConnectionSocketFactory> create()
                        .register("https", sslsf)
                        .register("http", new PlainConnectionSocketFactory())
                        .build();

        final BasicHttpClientConnectionManager connectionManager =
                new BasicHttpClientConnectionManager(socketFactoryRegistry);
        // NoopHostnameVerifier essentially turns hostname verification off
        final CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)
                .build();

        final HttpComponentsClientHttpRequestFactory requestFactory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        testRestTemplate = new RestTemplate(requestFactory);
        testRestTemplate.getMessageConverters().add(new StringHttpMessageConverter());
        requestHeaders = createHeaders(username, password);
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(List.of(MediaType.APPLICATION_JSON));
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
            @Serial
            private static final long serialVersionUID = 1L;
            {
                String auth = username + ":" + password;
                byte[] encodedAuth = Base64.encodeBase64(auth.getBytes(StandardCharsets.UTF_8));
                String authHeader = "Basic " + new String(encodedAuth);
                set("Authorization", authHeader);
            }
        };
    }
}