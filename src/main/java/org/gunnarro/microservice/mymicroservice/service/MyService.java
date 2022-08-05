package org.gunnarro.microservice.mymicroservice.service;

import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;
import org.springframework.stereotype.Service;

/**
 * TODO Remove or Refactor, this class is only servd as an example
 */
@Service
public interface MyService {
    Subscription getSubscription(Integer subscriptionId);
}
