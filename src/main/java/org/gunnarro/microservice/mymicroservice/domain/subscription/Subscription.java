package org.gunnarro.microservice.mymicroservice.domain.subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * TODO Remove or Refactor. This class only serves as an example
 */
@ApiModel(description = "Holds information about a subscription for a customer")
@JsonIgnoreProperties(ignoreUnknown = true)
@Data
@Builder
public class Subscription {
    @ApiModelProperty(notes = "Unique identifier of the subscription")
    @NotNull
    private Integer subscriptionId;
    @ApiModelProperty(notes = "Unique identifier of the customer. Owner of the subscription.")
    @NotNull
    private Integer customerId;
}
