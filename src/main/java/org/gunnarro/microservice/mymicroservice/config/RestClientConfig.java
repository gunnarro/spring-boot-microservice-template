package org.gunnarro.microservice.mymicroservice.config;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.util.Timeout;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Collections;

import org.gunnarro.microservice.mymicroservice.exception.ApplicationException;
import org.gunnarro.microservice.mymicroservice.rest.RestClient;

/**
 * TODO remove me if not used!
 * <p>
 * NOTE! client property names must be changed, all client.* properties must be
 * changed, the serves only as example
 * <p>
 * Generic configuration for rest templates for against external systems
 * This configuration serves only as a default setup.
 */
@Configuration
@Slf4j
public class RestClientConfig {
    @Value("${rest.client.trust.keystore}")
    private String keystoreFilename;
    @Value("${rest.client.trust.keystore.password}")
    private String keystorePassword;
    @Value("${rest.client.client.keystore}")
    private String clientKeystore;
    @Value("${rest.client.client.keystore.password}")
    private String clientKeystorePassword;
    @Value("${rest.client.service.connect.timeout}")
    private Integer connectTimeout;
    @Value("${rest.client.service.read.timeout}")
    private Integer readTimeout;
    @Value("${http.connectionpool.max-per-route}")
    private Integer maxConnectionPerRoute;
    @Value("${http.connectionpool.max-threads}")
    private Integer maxConnectionThreads;
    private final RestTemplateBuilder restTemplateBuilder;

    /**
     * Spring Boot Actuator manages the instrumentation of RestTemplate. For
     * that, you have to get injected with the RestTemplateBuilder.
     */
    public RestClientConfig(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

//    /**
//     * Rest template for client api
//     *
//     * @return
//     * @throws Exception
//     */
//     @Bean
//     @Qualifier("restClient")
//     public RestClient restClient() {
//     return createRestClient(env.getProperty("client.url"),
//     env.getProperty("client.user"), env.getProperty("client.pwd"));
//     }

    /**
     * Spring Boot Actuator manages the instrumentation of RestTemplate. For
     * that, you have to get injected with the RestTemplateBuilder.
     */
    private RestClient createRestClient(String url, String user, String pwd) {
        log.info("Client RestTemplate client url : {} ", url);
        log.info("Client RestTemplate client user: {} ", user);
        log.info("Client RestTemplate client pwd : {} ", pwd);
        log.info("Client RestTemplate client.http.connectionpool.max-per-route: {} ", maxConnectionPerRoute);
        log.info("Client RestTemplate client.http.connectionpool.max-threads: {} ", maxConnectionThreads);
        RestTemplate restTemplate = restTemplateBuilder.rootUri(url)
                .basicAuthentication(user, pwd)
                .build();
        restTemplate.setMessageConverters(Collections.singletonList(createMappingJackson2HttpMessageConverter()));
        restTemplate.setRequestFactory(useApacheHttpClientWithSsl());
        log.debug("Client rest template: {}", restTemplate);
        return new RestClient(url, restTemplate);
    }

    private MappingJackson2HttpMessageConverter createMappingJackson2HttpMessageConverter() {
        MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        // handle null values, simply skip null values
        mappingJackson2HttpMessageConverter.getObjectMapper().setSerializationInclusion(Include.NON_NULL);
        return mappingJackson2HttpMessageConverter;
    }

    private SSLConnectionSocketFactory getSSLConnectionSocketFactory() {
        try {
            SSLContext sslContext = SSLContexts.custom()
                    // load trust keystore, for trusted servers
                    .loadTrustMaterial(getKeyStore(keystoreFilename, keystorePassword), null)
                    // load client keystore, for client certs
                    .loadKeyMaterial(getKeyStore(clientKeystore, clientKeystorePassword), clientKeystorePassword.toCharArray())
                    .build();
            return new SSLConnectionSocketFactory(sslContext, new String[]{"TLSv1.2", "TLSv1.1"}, null, new NoopHostnameVerifier());
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    private KeyStore getKeyStore(String fileName, String pass) {
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            KeyStore trustKeyStore = KeyStore.getInstance("jks");
            trustKeyStore.load(fileInputStream, pass.toCharArray());
            log.info("loaded keystore: {}", fileName);
            return trustKeyStore;
        } catch (Exception e) {
            throw new ApplicationException(e.getMessage());
        }
    }

    /**
     * Create a new instance of the HttpComponentsClientHttpRequestFactory with
     * a default HttpClient that uses a default
     * org.apache.http.impl.conn.PoolingClientConnectionManager.
     */
    private HttpComponentsClientHttpRequestFactory useApacheHttpClientWithSsl() {
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(Timeout.ofMilliseconds(connectTimeout)) // ms
                .setConnectionRequestTimeout(Timeout.ofMilliseconds(readTimeout)) // ms
                //      .setSocketTimeout(Timeout.ofMilliseconds(readTimeout)) // ms
                .build();

        CloseableHttpClient httpClient = HttpClientBuilder.create()
                .setDefaultRequestConfig(config)
                //    .setSSLSocketFactory(getSSLConnectionSocketFactory())
                //    .setMaxConnPerRoute(maxConnectionPerRoute)
                //   .setMaxConnTotal(maxConnectionThreads)
                .build();

        HttpComponentsClientHttpRequestFactory useApacheHttpClient = new HttpComponentsClientHttpRequestFactory();
        useApacheHttpClient.setHttpClient(httpClient);
        return useApacheHttpClient;
    }
}