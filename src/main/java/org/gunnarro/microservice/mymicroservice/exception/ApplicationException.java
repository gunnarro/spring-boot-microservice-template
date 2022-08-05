package org.gunnarro.microservice.mymicroservice.exception;

/**
 * Generic application exception
 *
 */
public class ApplicationException extends RuntimeException {

    private static final long serialVersionUID = -5086299951349235980L;

    public ApplicationException(String message) {
        super(message);
    }
    
    public ApplicationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
