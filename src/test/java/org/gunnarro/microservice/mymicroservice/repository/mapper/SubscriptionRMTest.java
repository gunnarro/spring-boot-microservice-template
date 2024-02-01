package org.gunnarro.microservice.mymicroservice.repository.mapper;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;
import org.gunnarro.microservice.mymicroservice.repository.entity.Subscription;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.BDDMockito.given;

/**
 * TODO Remove or Refactor. This class only serves as an example.
 */
class SubscriptionRMTest extends DefaultTestConfig {

    @Mock
    private ResultSet resultSetMock;

    @Test
    void mapToSubscription() throws SQLException {
        given(resultSetMock.getLong("subscription_id")).willReturn(12345678L);
        // given(resultSetMock.getLong("customer_id")).willReturn(87654321L);

        SubscriptionRM subscriptionRowMapper = new SubscriptionRM();
        Subscription subscription = subscriptionRowMapper.mapRow(resultSetMock, 1);
        // Assertions.assertEquals(12345678L, subscription.getId());
        // Assertions.assertEquals(87654321, subscription.getCustomer().getId());
    }
}
