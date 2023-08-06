package org.gunnarro.microservice.mymicroservice.endpoint;

import org.gunnarro.microservice.mymicroservice.domain.dto.ErrorResponse;
import org.gunnarro.microservice.mymicroservice.domain.dto.subscription.SubscriptionDto;
import org.gunnarro.microservice.mymicroservice.service.MyService;
import io.micrometer.core.annotation.Timed;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * TODO Remove or Refactor. This class only serves as an example!
 */
@Tag(name = "RestService", description = "Example for rest service implementation")
@ApiResponses(value = {
        @ApiResponse(responseCode = HttpStatusMsg.HTTP_400_CODE, description = HttpStatusMsg.HTTP_400_MSG, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = HttpStatusMsg.HTTP_401_CODE, description = HttpStatusMsg.HTTP_401_MSG, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = HttpStatusMsg.HTTP_403_CODE, description = HttpStatusMsg.HTTP_403_MSG, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = HttpStatusMsg.HTTP_404_CODE, description = HttpStatusMsg.HTTP_404_MSG, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = HttpStatusMsg.HTTP_429_CODE, description = HttpStatusMsg.HTTP_429_MSG, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = HttpStatusMsg.HTTP_500_CODE, description = HttpStatusMsg.HTTP_500_MSG, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))}),
        @ApiResponse(responseCode = HttpStatusMsg.HTTP_503_CODE, description = HttpStatusMsg.HTTP_503_MSG, content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))})
})
@RestController
@RequestMapping(path = "/restservice/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestServiceController {
    private final static String REST_SERVICE_METRIC_NAME = "subscription.service.api";
    private final MyService myService;

    public RestServiceController(MyService myService) {
        this.myService = myService;
    }

    /**
     * Used to verify if REST is functional
     */
    @Operation(summary = "Check is service is up and running", description = "Simply check if this service is up and running")
    @GetMapping(path = "isalive")
    public ResponseEntity<Void> isAlive() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @Timed(value = REST_SERVICE_METRIC_NAME, description = "Measure frequency and latency for get subscription request")
    @Operation(summary = "Get subscription", description = "return subscription information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the subscription",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionDto.class))})
    })
    @GetMapping(path = "/subscriptions/{subscriptionId}")
    public ResponseEntity<SubscriptionDto> getSubscription(@PathVariable @NotNull Integer subscriptionId) {
        return ResponseEntity.ok(myService.getSubscription(subscriptionId));
    }

    @Timed(value = REST_SERVICE_METRIC_NAME, description = "Measure frequency and latency for create subscription request")
    @Operation(summary = "Create subscription", description = "Create new subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created the subscription",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionDto.class))})
    })
    @PostMapping(path = "/subscriptions")
    public ResponseEntity<SubscriptionDto> createSubscription(@RequestBody @Valid SubscriptionDto subscription) {
        log.info("create: {} ", subscription);
        return ResponseEntity.status(HttpStatus.CREATED).body(myService.saveSubscription(subscription));
    }

    @Timed(value = REST_SERVICE_METRIC_NAME, description = "Measure frequency and latency for update subscription request")
    @Operation(summary = "Update subscription", description = "Update subscription")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated the subscription",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionDto.class))})
    })
    @PutMapping(path = "/subscriptions/{subscriptionId}")
    public ResponseEntity<SubscriptionDto> updateSubscription(@PathVariable @NotNull Integer subscriptionId, @RequestBody @Valid SubscriptionDto subscription) {
        log.info("update: {} ", subscriptionId);
        return ResponseEntity.ok(myService.saveSubscription(subscription));
    }

    @Timed(value = REST_SERVICE_METRIC_NAME, description = "Measure frequency and latency for delete subscription request")
    @Operation(summary = "Delete subscription", description = "delete subscription with subscriptionId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Deleted the subscription",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SubscriptionDto.class))})
    })
    @DeleteMapping(path = "/subscriptions/{subscriptionId}")
    public ResponseEntity<Integer> deleteSubscription(@PathVariable @NotNull Integer subscriptionId) {
        log.info("delete: {} ", subscriptionId);
        myService.deleteSubscription(subscriptionId);
        return ResponseEntity.ok(subscriptionId);
    }
}
