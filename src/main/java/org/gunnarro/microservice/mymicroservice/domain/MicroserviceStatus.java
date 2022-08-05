package org.gunnarro.microservice.mymicroservice.domain;

import lombok.Getter;

@Getter
public enum MicroserviceStatus {
    APPLICATION_FAILURE(500100, "Application Failure"),
    SERVICE_ACCESS_NOT_ALLOWED(400100, "Service Access Not Allowed"),
    SERVICE_INPUT_VALIDATION_ERROR(400200, "Service Input Validation Error");

    private final int code;
    private final String description;

    MicroserviceStatus(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
