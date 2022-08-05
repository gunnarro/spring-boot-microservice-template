package org.gunnarro.microservice.mymicroservice.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Common error response for rest services
 */
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    @ApiModelProperty(notes = "http status code", example = "404")
    private Integer httpStatus;
    @ApiModelProperty(notes = "Http message", example = "Not Found")
    private String httpMessage;
    @ApiModelProperty(notes = "Internal error code", example = "4041001")
    private Integer errorCode;
    @ApiModelProperty(notes = "Description of the error", example = "Сustomer not found")
    private String description;
}