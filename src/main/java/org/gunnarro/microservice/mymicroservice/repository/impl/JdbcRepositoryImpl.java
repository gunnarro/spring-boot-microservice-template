package org.gunnarro.microservice.mymicroservice.repository.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;
import org.gunnarro.microservice.mymicroservice.exception.ApplicationException;
import org.gunnarro.microservice.mymicroservice.repository.DbRepository;

/**
 * TODO Remove or Refactor. This class only serves as an example!
 */
@Repository
@Slf4j
public class JdbcRepositoryImpl implements DbRepository {
    @Override
    public Subscription getSubscription(Integer subscriptionId) {
        try {
            log.debug("get subscription, subscriptionId={}", subscriptionId);
            // sql or stored procedure call
            return null;
        } catch (Exception ex) {
            // Pick up any repository errors and wrap it into an application exception
            // For all rest calls the application exception is handled and logged by the RestExceptionHandler
            // Do not rethrow the database error message, map into a new generic one
            throw new ApplicationException(String.format("Failed getting subscription, subscriptionId=%s", subscriptionId), ex);
        }
    }

    @Override
    public Subscription createSubscription(Subscription subscription) {
        return Subscription.builder().subscriptionId(subscription.getSubscriptionId()).build();
    }

    @Override
    public Subscription updateSubscription(Subscription subscription) {
        return Subscription.builder().subscriptionId(subscription.getSubscriptionId()).build();
    }

    @Override
    public void deleteSubscription(Integer subscriptionId) {
    }
}
