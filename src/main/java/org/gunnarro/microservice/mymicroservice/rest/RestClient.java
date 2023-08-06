package org.gunnarro.microservice.mymicroservice.rest;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.RequestEntity.BodyBuilder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.gunnarro.microservice.mymicroservice.domain.dto.Parameter;
import org.gunnarro.microservice.mymicroservice.exception.ApplicationException;
import org.gunnarro.microservice.mymicroservice.exception.RestClientApiException;

/**
 * TODO Remove if not used. RestClient should only be used when calling other
 * rest services!
 * 
 * Generic rest client
 * 
 * @author mentos
 *
 */
@Slf4j
public class RestClient {
    private static final String ACCEPT_APP_JSON = "application/json";
    private static final MediaType[] DEFAULT_ACCEPTS = new MediaType[] { MediaType.parseMediaType(ACCEPT_APP_JSON) };
    private static final MediaType DEFAULT_CONTENT_TYPE = MediaType.parseMediaType(ACCEPT_APP_JSON);

    // generic adb url query parameters
    // comma separated list of field names, for example: id,name,value
    public static final String URL_PARAM_FIELDS = "fields";
    public static final String URL_PARAM_SORT_BY = "sortBy";
    public static final String URL_PARAM_FILTER_BY_VALUE = "filterByValue";
    public static final String URL_PARAM_FILTER_BY = "filterBy";
    public static final String URL_PARAM_LIMIT = "limit";
    public static final String URL_PARAM_LIMIT_DEFAULT_VALUE = "250";

    private RestTemplate restTemplate;
    private String basePath;

    public enum CollectionFormat {
        CSV(","), TSV("\t"), SSV(" "), PIPES("|"), MULTI(null);

        private final String separator;

        private CollectionFormat(String separator) {
            this.separator = separator;
        }

        private String collectionToString(Collection<? extends CharSequence> collection) {
            return StringUtils.collectionToDelimitedString(collection, separator);
        }
    }

    /**
     * 
     * @param basePath
     * @param restTemplate
     */
    public RestClient(String basePath, RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.basePath = basePath;
        for (Object o : restTemplate.getInterceptors()) {
            log.info("{}", (o));
        }
        log.info("init, baseUrl={}", basePath);
    }

    public static Map<String, Object> createAndValidateUriParameter(String name, String value) {
        checkParameter(name, value);
        Map<String, Object> map = new HashMap<>();
        map.put(name, value);
        return map;
    }

    public static Map<String, Object> createAndValidateUriParameter(Parameter[] params) {
        Map<String, Object> map = new HashMap<>();
        for (Parameter p : params) {
            checkParameter(p.getName(), p.getValue());
            map.put(p.getName(), p.getValue());
        }
        return map;
    }

    /**
     * Converts a parameter to a {@link MultiValueMap} for use in REST requests
     * 
     * @param collectionFormat The format to convert to
     * @param name The name of the parameter
     * @param value The parameter's value
     * @return a Map containing the String value(s) of the input parameter
     */
    public static MultiValueMap<String, String> parameterToMultiValueMap(CollectionFormat collectionFormat, String name, Object value) {
        final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();

        if (name == null || name.isEmpty() || value == null) {
            return params;
        }

        if (collectionFormat == null) {
            collectionFormat = CollectionFormat.CSV;
        }

        Collection<?> valueCollection = null;
        if (value instanceof Collection) {
            valueCollection = (Collection<?>) value;
        } else {
            params.add(name, parameterToString(value));
            return params;
        }

        if (valueCollection.isEmpty()) {
            return params;
        }

        if (collectionFormat.equals(CollectionFormat.MULTI)) {
            for (Object item : valueCollection) {
                params.add(name, parameterToString(item));
            }
            return params;
        }

        List<String> values = new ArrayList<>();
        for (Object o : valueCollection) {
            values.add(parameterToString(o));
        }
        params.add(name, collectionFormat.collectionToString(values));

        return params;
    }

    /**
     * Format the given parameter object into string.
     * 
     * @param param the object to convert
     * @return String the parameter represented as a String
     */
    public static String parameterToString(Object param) {
        if (param == null) {
            return "";
        } else if (param instanceof Collection) {
            StringBuilder b = new StringBuilder();
            for (Object o : (Collection<?>) param) {
                if (b.length() > 0) {
                    b.append(",");
                }
                b.append(String.valueOf(o));
            }
            return b.toString();
        } else {
            return String.valueOf(param);
        }
    }

    protected static URI buildUri(String basePath, String path, Map<String, Object> uriParameters, Map<String, String> queryParams) {
        if (basePath == null || basePath.isEmpty()) {
            throw new ApplicationException("Url basePath can not be null or empty!");
        }
        UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(basePath).path(path);
        if (queryParams != null) {
            for (Entry<String, String> entry : queryParams.entrySet()) {
                uriBuilder = uriBuilder.queryParam(entry.getKey(), entry.getValue());
            }
        }
        if (uriParameters != null) {
            return uriBuilder.buildAndExpand(uriParameters).toUri();
        }
        return uriBuilder.build().toUri();
    }

    /**
     * 
     * @param path
     * @param uriParameters
     * @param method
     * @return
     */
    public <T> T invokeAPIDelete(String path, Map<String, Object> uriParameters) {
        return invokeAPI(buildUri(basePath, path, uriParameters, null), HttpMethod.DELETE, new HashMap<>(), new ParameterizedTypeReference<T>() {
        });
    }

    /**
     * 
     * @param path
     * @param uriParameters
     * @param method
     * @param bodyObj
     * @return
     */
    public <T> T invokeAPIPut(String path, Map<String, Object> uriParameters, Object bodyObj, ParameterizedTypeReference<T> returnType) {
        return invokeAPI(buildUri(basePath, path, uriParameters, null), HttpMethod.PUT, bodyObj, returnType);
    }

    /**
     * 
     * @param path
     * @param uriParameters
     * @param method
     * @param bodyObj
     * @return
     */
    public <T> T invokeAPIGet(String path, Map<String, Object> uriParameters, Map<String, String> queryParameters, ParameterizedTypeReference<T> returnType) {
        return invokeAPI(buildUri(basePath, path, uriParameters, queryParameters), HttpMethod.GET, null, returnType);
    }

    /**
     * 
     * @param path
     * @param uriParameters
     * @param method
     * @param bodyObj
     * @return
     */
    public <T> T invokeAPIGet(String path, Map<String, Object> uriParameters, ParameterizedTypeReference<T> returnType) {
        return invokeAPIGet(path, uriParameters, null, returnType);
    }

    /**
     * 
     * @param path The sub-path of the HTTP URL
     * @param uriParameters
     * @param bodyObj
     * @param returnType The return type into which to deserialize the response.
     *            Set equal to null if no response is required.
     * @return
     */
    public <T> T invokeAPIPost(String path, Map<String, Object> uriParameters, Object bodyObj, ParameterizedTypeReference<T> returnType) {
        return invokeAPI(buildUri(basePath, path, uriParameters, null), HttpMethod.POST, bodyObj, returnType);
    }

    /**
     * Invoke API by sending HTTP request with the given options.
     *
     * @param <T> the return type to use
     * @param path The sub-path of the HTTP URL
     * @param method The request method
     * @param bodyObj The request body object. use null if none.
     * @param returnType The return type into which to deserialize the response.
     *            Set equal to null if no response is required.
     * @return The response body in chosen <T> type. Returns null if no content,
     *         and returns null if returnType is set equal to null.
     * @throws RestClientApiException
     */
    private <T> T invokeAPI(URI uri, HttpMethod method, Object bodyObj, ParameterizedTypeReference<T> returnType) {
        log.debug("call api uri={}, method={}, body={}, returnType={}", uri, method, bodyObj, returnType);
        RequestEntity<Object> requestEntity = buildRequest(uri, method, bodyObj);
        try {
            // call api
            // Note! may cause json parse problem if the client return error
            // object and not expected return type
            // but all 4xx statuses should be caught by the
            // HttpClientErrorException and simply return null.
            ResponseEntity<T> responseEntity = restTemplate.exchange(requestEntity, returnType);
            Object bodyClass = responseEntity.getBody();
            log.debug("responseEntity: {}", responseEntity);
            log.debug("class: {}", responseEntity.getClass());
            log.debug("body class: {}", bodyClass);
            log.debug("http successful calling api, uri={}, method={}, httpStatus={}, response={}", uri.getPath(), method, responseEntity.getStatusCode(),
                    responseEntity.getBody());
            return responseEntity.getBody();
        } catch (HttpClientErrorException e) {
            if (e.getStatusCode().compareTo(HttpStatus.NOT_FOUND) == 0 || e.getStatusCode().compareTo(HttpStatus.NO_CONTENT) == 0) {
                log.debug("http returned 4xx status, calling api, uri={}, method={}, httpStatus={}, message={}, return null.}", uri.getPath(), method,
                        e.getStatusCode().value(), e.getResponseBodyAsString());
                return null;
            } else {
                String errorMsg = String.format("failed calling api, uri=%s, method=%s, httpStatus=%s, error=%s, bodyObj=%s", uri.getPath(), method,
                        e.getStatusCode().value(), e.getResponseBodyAsString(), bodyObj);
                throw new RestClientApiException(String.format("%s, %s", e.getMessage(), errorMsg));
            }
        } catch (RestClientException rce) {
            String errMsg = String.format("failed calling uri=%s, method=%s, body=%s", uri.getPath(), method, bodyObj);
            throw new RestClientApiException(
                    String.format("Error=%s, Exception=%s, cause=%s, root cause=%s", errMsg, rce.getMessage(), rce.getRootCause(), rce.getMostSpecificCause()));
        }
    }

    protected RequestEntity<Object> buildRequest(URI uri, HttpMethod method, Object bodyObj) {
        BodyBuilder requestBuilder = RequestEntity.method(method, uri);
        // set request's Accept header
        requestBuilder.accept(DEFAULT_ACCEPTS);
        // set request's Content-Type header
        requestBuilder.contentType(DEFAULT_CONTENT_TYPE);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setAccept(Arrays.asList(DEFAULT_ACCEPTS));
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        addHeadersToRequest(httpHeaders, requestBuilder);
        RequestEntity<Object> request = requestBuilder.body(bodyObj);
        log.debug("request: {}", request);
        return request;
    }

    /**
     * Add headers to the request that is being built
     * 
     * @param headers The headers to add
     * @param requestBuilder The current request
     */
    private void addHeadersToRequest(HttpHeaders headers, BodyBuilder requestBuilder) {
        for (Entry<String, List<String>> entry : headers.entrySet()) {
            List<String> values = entry.getValue();
            for (String value : values) {
                if (value != null) {
                    requestBuilder.header(entry.getKey(), value);
                }
            }
        }
    }

    private static void checkParameter(String name, String value) {
        // Verify that the required parameter name is not null or empty
        if (name == null || name.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("Required parameter is missing! name=%s", name));
        }
        // Verify that the required parameter value is not null or empty
        if (value == null || value.isEmpty()) {
            throw new HttpClientErrorException(HttpStatus.BAD_REQUEST, String.format("Required parameter value is not set! name=%s, value=%s", name, value));
        }
    }
}
