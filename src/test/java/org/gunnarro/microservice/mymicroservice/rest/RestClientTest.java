package org.gunnarro.microservice.mymicroservice.rest;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;
import org.gunnarro.microservice.mymicroservice.domain.dto.Parameter;
import org.gunnarro.microservice.mymicroservice.exception.ApplicationException;
import org.gunnarro.microservice.mymicroservice.rest.RestClient.CollectionFormat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

class RestClientTest extends DefaultTestConfig {
    @BeforeEach
    public void init() {
    }

    @Test
    void createAndValidateUriParameters() {
        Parameter[] params = new Parameter[2];
        params[0] = Parameter.builder()
                .name("name1")
                .value("value1")
                .build();
        params[1] = Parameter.builder()
                .name("name2")
                .value("value2")
                .build();
        Map<String, Object> paramMap = RestClient.createAndValidateUriParameter(params);
        Assertions.assertEquals(2, paramMap.size());
        Assertions.assertEquals("value1", paramMap.get("name1"));
        Assertions.assertEquals("value2", paramMap.get("name2"));
    }

    @Test
    void createAndValidateUriParameter_ok() {
        Assertions.assertNotNull(RestClient.createAndValidateUriParameter("name", "value"));
    }

    @Test
    void createAndValidateUriParameter_value_null() {
        HttpClientErrorException thrown = Assertions.assertThrows(HttpClientErrorException.class, () -> {
            RestClient.createAndValidateUriParameter("name", null);
        });

        Assertions.assertEquals("400 Required parameter value is not set! name=name, value=null", thrown.getMessage());
        Assertions.assertTrue(Objects.requireNonNull(thrown.getMessage()).contains("Required parameter value is not set"));
    }

    @Test
    void buildUri_basepath_null() {
        ApplicationException thrown = Assertions.assertThrows(ApplicationException.class, () -> {
            RestClient.buildUri(null, "/accounts", null, null);
        });

        Assertions.assertEquals("Url basePath can not be null or empty!", thrown.getMessage());
    }

    @Test
    void buildUri_basepath_empty() {
        ApplicationException thrown = Assertions.assertThrows(ApplicationException.class, () -> {
            RestClient.buildUri(null, "/accounts", null, null);
        });

        Assertions.assertEquals("Url basePath can not be null or empty!", thrown.getMessage());
    }

    @Test
    void buildUri() {
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put(RestClient.URL_PARAM_SORT_BY, "name");

        Assertions.assertEquals("http://gunnarro.no/accounts", RestClient.buildUri("http://gunnarro.no", "/accounts", null, null).toString());
        Assertions.assertEquals("http://gunnarro.no/accounts?sortBy=name", RestClient.buildUri("http://gunnarro.no", "/accounts", null, queryParams).toString());
        final Map<String, Object> uriVariables = new HashMap<String, Object>();
        uriVariables.put("accountId", "12345678");
        uriVariables.put("profileId", "11");
        Assertions.assertEquals("http://gunnarro.no/accounts/12345678/profile/11",
                RestClient.buildUri("http://gunnarro.no", "/accounts/{accountId}/profile/{profileId}", uriVariables, null).toString());
        Assertions.assertEquals("http://gunnarro.no/accounts/12345678/properties?sortBy=name",
                RestClient.buildUri("http://gunnarro.no", "/accounts/{accountId}/properties", uriVariables, queryParams).toString());

        String path = UriComponentsBuilder.fromPath("/accounts/{accountId}/profile/{profileId}").queryParam("sortBy", "accountId").buildAndExpand(uriVariables)
                .toUriString();
        Assertions.assertEquals("/accounts/12345678/profile/11?sortBy=accountId", path);

        // Query parameters
        String path2 = UriComponentsBuilder.fromPath("/accounts/{accountId}/profile/{profileId}").queryParam("filterBy", "transactionId")
                .queryParam("filterByValue", "trans_111111_*").buildAndExpand(uriVariables).toUriString();
        Assertions.assertEquals("/accounts/12345678/profile/11?filterBy=transactionId&filterByValue=trans_111111_*", path2);
    }

    @Test
    void parameterToMultiValueMap() {
        MultiValueMap<String, String> multiValueMap = RestClient.parameterToMultiValueMap(CollectionFormat.CSV, "name", "value1,value2");
        Assertions.assertEquals("{name=[value1,value2]}", multiValueMap.toString());

        multiValueMap = RestClient.parameterToMultiValueMap(CollectionFormat.MULTI, "multi", "value1,value2");
        Assertions.assertEquals("{multi=[value1,value2]}", multiValueMap.toString());

        multiValueMap = RestClient.parameterToMultiValueMap(CollectionFormat.SSV, "ssv", "value1,value2");
        Assertions.assertEquals("{ssv=[value1,value2]}", multiValueMap.toString());

        multiValueMap = RestClient.parameterToMultiValueMap(CollectionFormat.CSV, "name", null);
        Assertions.assertTrue(multiValueMap.isEmpty());
    }
}
