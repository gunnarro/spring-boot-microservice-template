package org.gunnarro.microservice.mymicroservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.gunnarro.microservice.mymicroservice.exception.NotFoundException;
import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;
import org.gunnarro.microservice.mymicroservice.service.MyService;
import org.gunnarro.microservice.mymicroservice.repository.DbRepository;

/**
 * TODO Remove or Refactor. This class only serves as an example
 */
@Service
@Slf4j
public class MyServiceImpl implements MyService {
    private final DbRepository dbRepository;

    public MyServiceImpl(DbRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Subscription getSubscription(Integer subscriptionId) {
        Subscription subscription = dbRepository.getSubscription(subscriptionId);
        log.debug("returned {}", subscription);
        if (subscription == null) {
            throw new NotFoundException(String.format("subscription not found, subscriptionId=%s", subscriptionId));
        }
        return subscription;
    }
}
