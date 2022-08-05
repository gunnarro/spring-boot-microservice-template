package org.gunnarro.microservice.mymicroservice.endpoint;

import org.gunnarro.microservice.mymicroservice.domain.ErrorResponse;
import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;
import org.gunnarro.microservice.mymicroservice.service.MyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * TODO Remove or Refactor. This class only serves as an example!
 */
@Api(value = "restservice-v1", tags = {"RestService"}, protocols = "HTTPS", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
@ApiResponses(value = {
        @ApiResponse(code = HttpStatusMsg.HTTP_400_CODE, message = HttpStatusMsg.HTTP_400_MSG, response = ErrorResponse.class),
        @ApiResponse(code = HttpStatusMsg.HTTP_401_CODE, message = HttpStatusMsg.HTTP_401_MSG, response = ErrorResponse.class),
        @ApiResponse(code = HttpStatusMsg.HTTP_403_CODE, message = HttpStatusMsg.HTTP_403_MSG, response = ErrorResponse.class),
        @ApiResponse(code = HttpStatusMsg.HTTP_404_CODE, message = HttpStatusMsg.HTTP_404_MSG, response = ErrorResponse.class),
        @ApiResponse(code = HttpStatusMsg.HTTP_429_CODE, message = HttpStatusMsg.HTTP_429_MSG, response = ErrorResponse.class),
        @ApiResponse(code = HttpStatusMsg.HTTP_500_CODE, message = HttpStatusMsg.HTTP_500_MSG, response = ErrorResponse.class),
        @ApiResponse(code = HttpStatusMsg.HTTP_503_CODE, message = HttpStatusMsg.HTTP_503_MSG, response = ErrorResponse.class)})
@RestController
@RequestMapping(path = "/restservice/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestServiceController {
    private final MyService myService;

    public RestServiceController(MyService myService) {
        this.myService = myService;
    }

    /**
     * Used to verify if REST is functional
     */
    @ApiOperation(value = "isAlive", notes = "Simply check if this service is up and running")
    @GetMapping("isalive")
    public ResponseEntity<Void> isAlive() {
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @ApiOperation(value = "Get subscription", notes = "return subscription information", response = Subscription.class)
    @GetMapping(path = "/subscriptions/{subscriptionId}")
    public ResponseEntity<Subscription> getSubscription(@PathVariable @NotBlank Integer subscriptionId) {
        // TODO call service layer
        Subscription subscription = myService.getSubscription(subscriptionId);
        return ResponseEntity.ok(subscription);
    }

    @ApiOperation(value = "Update subscription", notes = "Update subscription", response = Subscription.class)
    @PutMapping(path = "/subscriptions/{subscriptionId}")
    public ResponseEntity<Subscription> updateSubscription(@PathVariable @NotBlank Integer subscriptionId, @RequestBody @Valid Subscription subscription) {
        log.info("subscription : {} ", subscription);
        // TODO call service layer
        return ResponseEntity.ok().build();
    }
}