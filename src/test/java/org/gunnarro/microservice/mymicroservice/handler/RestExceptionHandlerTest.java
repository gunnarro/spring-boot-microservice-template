package  org.gunnarro.microservice.mymicroservice.handler;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.client.HttpClientErrorException;

import  org.gunnarro.microservice.mymicroservice.domain.dto.ErrorResponse;
import  org.gunnarro.microservice.mymicroservice.exception.ApplicationException;   
import  org.gunnarro.microservice.mymicroservice.exception.RestInputValidationException;

class RestExceptionHandlerTest {

    @Test
    void handleApplicationException() {
        RestExceptionHandler handler = new RestExceptionHandler();
        ResponseEntity<ErrorResponse> errorResponse = handler
                .handleApplicationException(new ApplicationException("internal application error!", new RuntimeException("stactrace for root casue")));
        Assertions.assertEquals(500, errorResponse.getStatusCodeValue());
        Assertions.assertEquals(500, errorResponse.getBody().getHttpStatus());
        Assertions.assertEquals("Internal Server Error", errorResponse.getBody().getHttpMessage());
        Assertions.assertEquals("Application Failure", errorResponse.getBody().getDescription());
        Assertions.assertEquals(500100, errorResponse.getBody().getErrorCode());
    }

    @Test
    void handleAccessDeniedException() {
        RestExceptionHandler handler = new RestExceptionHandler();
        ResponseEntity<ErrorResponse> errorResponse = handler.handleAccessDeniedException(new AccessDeniedException("Access denied!"));
        Assertions.assertEquals(403, errorResponse.getStatusCodeValue());
        Assertions.assertEquals(403, errorResponse.getBody().getHttpStatus());
        Assertions.assertEquals("Forbidden", errorResponse.getBody().getHttpMessage());
        Assertions.assertEquals("Service Access Not Allowed", errorResponse.getBody().getDescription());
        Assertions.assertEquals(400100, errorResponse.getBody().getErrorCode());
    }

    /**
     * TODO check if we need this when using the @Valid annotation in rest endpoints
     */
    @Test
    void handleBadRequestException() {
        RestExceptionHandler handler = new RestExceptionHandler();
        ResponseEntity<ErrorResponse> errorResponse = handler.handleBadRequestException(new RestInputValidationException("Bad request"));
        Assertions.assertEquals(400, errorResponse.getStatusCodeValue());
        Assertions.assertEquals(400, errorResponse.getBody().getHttpStatus());
        Assertions.assertEquals("Bad Request", errorResponse.getBody().getHttpMessage());
        Assertions.assertEquals("Service Input Validation Error", errorResponse.getBody().getDescription());
        Assertions.assertEquals(400200, errorResponse.getBody().getErrorCode());
    }

    @Test
    void handleHttpClientErrorException() {
        RestExceptionHandler handler = new RestExceptionHandler();
        ResponseEntity<ErrorResponse> errorResponse = handler.handleHttpClientErrorException(new HttpClientErrorException(HttpStatus.BAD_REQUEST));
        Assertions.assertEquals(404, errorResponse.getStatusCodeValue());
        Assertions.assertEquals(404, errorResponse.getBody().getHttpStatus());
        Assertions.assertEquals("Not Found", errorResponse.getBody().getHttpMessage());
        Assertions.assertNull(errorResponse.getBody().getDescription());
        Assertions.assertNull(errorResponse.getBody().getErrorCode());
    }
}
