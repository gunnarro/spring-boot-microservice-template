package org.gunnarro.microservice.mymicroservice.domain.subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * TODO Remove or Refactor. This class only serves as an example
 */
@ApiModel(description = "Holds information about a subscription for a customer")
@JsonIgnoreProperties(ignoreUnknown = true)
NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Subscription {
    @ApiModelProperty(notes = "Unique identifier of the subscription.")
    @NotNull
    private Integer subscriptionId;
    @ApiModelProperty(notes = "Unique identifier of the customer. Owner of the subscription.")
    @NotNull
    private Integer customerId;
    @ApiModelProperty(notes = "Name of subscription")
    @Pattern(regexp = "[a-zA-Z]+", message = "Can only contain lower and uppercase alphabetic chars.")
    private String name;
    @ApiModelProperty(notes = "Type of subscription.")
    @Pattern(regexp = "[a-zA-Z]+", message = "Can only contain lower and uppercase alphabetic chars.")
    private String type;
    @ApiModelProperty(notes = "user password")
    @Pattern(regexp = "[a-zA-Z0-9]+", message = "Can not contain any special characters or blanks.")
    private String password;
}
