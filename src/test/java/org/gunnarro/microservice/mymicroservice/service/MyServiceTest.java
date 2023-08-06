package org.gunnarro.microservice.mymicroservice.service;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;
import org.gunnarro.microservice.mymicroservice.repository.entity.Subscription;
import org.gunnarro.microservice.mymicroservice.exception.NotFoundException;
import org.gunnarro.microservice.mymicroservice.repository.DbRepository;
import org.gunnarro.microservice.mymicroservice.repository.impl.JdbcRepositoryImpl;
import org.gunnarro.microservice.mymicroservice.service.impl.MyServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.ContextConfiguration;

/**
 * TODO Remove or Refactor. This class only serves as an example.
 */
@ContextConfiguration(classes = {MyServiceImpl.class, JdbcRepositoryImpl.class})
class MyServiceTest extends DefaultTestConfig {
    @Mock
    private DbRepository dbRepositoryMock;
    @InjectMocks
    private MyServiceImpl service;

    @Test
    void getSubscription() {
        Mockito.when(dbRepositoryMock.getSubscription(12345678)).thenReturn(Subscription.builder().build());
        Assertions.assertNotNull(service.getSubscription(12345678));
    }

    @Test
    void getSubscriptionNotFound() {
        Mockito.when(dbRepositoryMock.getSubscription(12345678)).thenReturn(null);
        NotFoundException exception = Assertions.assertThrows(NotFoundException.class,
                () -> service.getSubscription(12345678));
        Assertions.assertEquals("subscription not found, subscriptionId=12345678", exception.getMessage());
    }
}
