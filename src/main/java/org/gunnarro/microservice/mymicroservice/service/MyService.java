package org.gunnarro.microservice.mymicroservice.service;

import org.gunnarro.microservice.mymicroservice.domain.dto.subscription.SubscriptionDto;
import org.springframework.stereotype.Service;

/**
 * TODO Remove or Refactor, this class is only servd as an example
 */
@Service
public interface MyService {
    SubscriptionDto getSubscription(Integer subscriptionId);

    SubscriptionDto saveSubscription(SubscriptionDto subscription);

    void deleteSubscription(Integer subscriptionId);
}
