package org.gunnarro.microservice.mymicroservice.config;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.apache.logging.log4j.ThreadContext;
import lombok.extern.slf4j.Slf4j;

/**
 * Filter for setting common HTTP Headers for all rest request and responses.
 * Add a UUID to both request and response http header.
 * If a request contains a valid a UUID request header, that is used, otherwise a new one is generated.
 * 
 */
@Slf4j
public class HttpResponseHeaderFilter implements Filter {
    private static final String HTTP_HEADER_UUID = "UUID";

    @Override
    public void destroy() {
        log.debug("destroy UUID {}", ThreadContext.get(HTTP_HEADER_UUID));
        ThreadContext.clearAll();
    }

    /**
     * Custom filer for reading and setting UUID http header
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.debug("custom http filter");
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        // Read the uuid from incoming request and use that if exist, if not generate and use a new uuid
        String validUuid = validateUuid(httpServletRequest.getHeader(HTTP_HEADER_UUID));
        ThreadContext.put(HTTP_HEADER_UUID, validUuid);
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader(HTTP_HEADER_UUID, validUuid);
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig arg0) {
        log.debug("init");
    }

    private String validateUuid(String uuid) {
      try {
        if (uuid != null) {
           return UUID.fromString(uuid).toString();
        }
      } catch (IllegalArgumentException e) {
        // this eas an invalid uuid, simply ignore and generate a new uuid.
      }
      return UUID.randomUUID().toString();
     }
}
