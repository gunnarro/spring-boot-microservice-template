package org.gunnarro.microservice.mymicroservice.exception;

/**
 * Generic exception for Rest Client http errors
 * TODO Remove if not in use
 */
public class RestClientApiException extends RuntimeException {

    private static final long serialVersionUID = 2435326436456L;

    public RestClientApiException(String message) {
        super(message);
    }

}
