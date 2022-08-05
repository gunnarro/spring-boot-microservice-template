package org.gunnarro.microservice.mymicroservice.exception;

/**
 * Generic exception when a resource is not found
 * TODO Remove if not in use
 */
public class NotFoundException extends RuntimeException {

    private static final long serialVersionUID = -50646895745637680L;

    public NotFoundException(String message) {
        super(message);
    }
}
