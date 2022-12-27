package org.gunnarro.microservice.mymicroservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * Common error response for rest services
 */
@Schema(description = "Holds rest service error description")
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    @Schema(description = "http status code", example = "404")
    private Integer httpStatus;
    @Schema(description = "Http message", example = "Not Found")
    private String httpMessage;
    @Schema(description = "Internal error code", example = "4041001")
    private Integer errorCode;
    @Schema(description = "Description of the error", example = "Customer not found")
    private String description;
}