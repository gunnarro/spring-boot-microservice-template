package org.gunnarro.microservice.mymicroservice.repository;

import org.springframework.stereotype.Repository;

import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;

/**
 * TODO Remove or Refactor. This class only serves as an example.
 */
@Repository
public interface DbRepository {
    Subscription getSubscription(Integer subscriptionId);
}
//TODO Foreslår at vi ikke overkompleserer koden med interfaces unødvendig
