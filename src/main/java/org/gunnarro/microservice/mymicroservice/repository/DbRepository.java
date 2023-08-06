package org.gunnarro.microservice.mymicroservice.repository;

import org.springframework.stereotype.Repository;
import org.gunnarro.microservice.mymicroservice.repository.entity.Subscription;

/**
 * TODO Remove or Refactor. This class only serves as an example.
 */
@Repository
public interface DbRepository {
    Subscription getSubscription(Integer subscriptionId);

    Subscription createSubscription(Subscription subscription);

    Subscription updateSubscription(Subscription subscription);

    void deleteSubscription(Integer subscriptionId);
}

