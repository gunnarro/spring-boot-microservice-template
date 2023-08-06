package org.gunnarro.microservice.mymicroservice.endpoint;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;
import org.gunnarro.microservice.mymicroservice.repository.impl.JdbcRepositoryImpl;
import org.gunnarro.microservice.mymicroservice.service.impl.MyServiceImpl;
import org.gunnarro.microservice.mymicroservice.domain.dto.subscription.SubscriptionDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * TODO Remove or Refactor. This class only serves as an example
 */
@ContextConfiguration(classes = { MyServiceImpl.class, JdbcRepositoryImpl.class })
class RestServiceControllerTest extends DefaultTestConfig {
    @Mock
    private MyServiceImpl myServiceMock;
    @InjectMocks
    private RestServiceController restServiceController;

    @Test
    void isAlive() {
        ResponseEntity<Void> response = restServiceController.isAlive();
        assertThat(response).isNotNull();
        Assertions.assertEquals("<200 OK OK,[]>", response.toString());
    }

    @Test
    void getSubscription() {
        SubscriptionDto subscription = SubscriptionDto.builder().subscriptionId(23L).customerId(123L).name("test").password("test-pass").build();
        when(myServiceMock.getSubscription(23)).thenReturn(subscription);
        ResponseEntity<SubscriptionDto> response = restServiceController.getSubscription(23);
        assertThat(response.getBody()).isNotNull();
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(23, response.getBody().getSubscriptionId());
    }

    @Test
    void createSubscription() {
        SubscriptionDto subscription = SubscriptionDto.builder().subscriptionId(23L).customerId(123L).name("test").password("test-pass").build();
        when(myServiceMock.saveSubscription(any())).thenReturn(subscription);
        ResponseEntity<SubscriptionDto> response = restServiceController.createSubscription(subscription);
        assertThat(response.getBody()).isNotNull();
        Assertions.assertEquals(201, response.getStatusCode().value());
        Assertions.assertEquals(23, response.getBody().getSubscriptionId());
    }

    @Test
    void updateSubscription() {
        SubscriptionDto subscription = SubscriptionDto.builder().subscriptionId(23L).customerId(123L).name("test").password("test-pass").build();
        when(myServiceMock.saveSubscription(any())).thenReturn(subscription);
        ResponseEntity<SubscriptionDto> response = restServiceController.updateSubscription(subscription.getSubscriptionId().intValue(), subscription);
        assertThat(response.getBody()).isNotNull();
        Assertions.assertEquals(200, response.getStatusCode().value());
        Assertions.assertEquals(23, response.getBody().getSubscriptionId());
    }

    @Test
    void deleteSubscription() {
        ResponseEntity<Integer> response = restServiceController.deleteSubscription(23);
        assertThat(response).isNotNull();
        Assertions.assertEquals(200, response.getStatusCode().value());
    }
}