package org.gunnarro.microservice.mymicroservice.exception;

/**
 * Generic exception for rest service input parameter validation
 * TODO Remove if not in use
 */
public class RestInputValidationException extends RuntimeException {

    private static final long serialVersionUID = -5086299951349235980L;

    public RestInputValidationException(String message) {
        super(message);
    }
}
