package org.gunnarro.microservice.mymicroservice.domain.subscription;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * TODO Remove or Refactor. This class only serves as an example
 */
@Schema(description = "Holds information about a subscription for a customer")
@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Subscription {
    @Schema(description = "Unique identifier of the subscription.")
    @NotNull
    private Integer subscriptionId;
    @Schema(description = "Unique identifier of the customer. Owner of the subscription.")
    @NotNull
    private Integer customerId;
    @Schema(description = "Name of subscription")
    @Pattern(regexp = "[a-zA-Z]{1,50}", message = "Can only contain lower and uppercase alphabetic chars. Min 1 char, max 50 chars.")
    private String name;
    @Schema(description = "Type of subscription.")
    @Pattern(regexp = "[a-zA-Z]{1,10}", message = "Can only contain lower and uppercase alphabetic chars. Min 1 char, max 10 chars.")
    private String type;
    @Schema(description = "user password")
    @Pattern(regexp = "[a-zA-Z0-9]{8,25}", message = "Can contain any characters and digits. Min 8 char, max 25 chars.")
    private String password;
}
