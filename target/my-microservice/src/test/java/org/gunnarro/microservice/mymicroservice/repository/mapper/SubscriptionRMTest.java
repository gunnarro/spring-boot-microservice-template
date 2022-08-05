package org.gunnarro.microservice.mymicroservice.repository.mapper;

import static org.mockito.BDDMockito.given;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import org.gunnarro.microservice.mymicroservice.DefaultTestConfig;
import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;

/**
 * TODO Remove or Refactor. This class only serves as an example.
 *
 */
class SubscriptionRMTest extends DefaultTestConfig {

    @Mock
    private ResultSet resultSetMock;

    @Test
    void mapToSubscription() throws SQLException {
        given(resultSetMock.getInt("abonnent_nr")).willReturn(12345678);
        given(resultSetMock.getInt("kurt_id")).willReturn(87654321);

        SubscriptionRM subscriptionRowMapper = new SubscriptionRM();
        Subscription subscription = subscriptionRowMapper.mapRow(resultSetMock, 1);
        Assertions.assertEquals(12345678, subscription.getSubscriptionId());
        Assertions.assertEquals(87654321, subscription.getKurtId());
    }
}
