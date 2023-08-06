package org.gunnarro.microservice.mymicroservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import org.gunnarro.microservice.mymicroservice.exception.NotFoundException;
import org.gunnarro.microservice.mymicroservice.domain.dto.subscription.SubscriptionDto;
import org.gunnarro.microservice.mymicroservice.domain.mapper.SubscriptionMapper;
import org.gunnarro.microservice.mymicroservice.service.MyService;
import org.gunnarro.microservice.mymicroservice.repository.DbRepository;
import org.gunnarro.microservice.mymicroservice.repository.entity.Subscription;

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
    public SubscriptionDto getSubscription(Integer subscriptionId) {
        Subscription subscription = dbRepository.getSubscription(subscriptionId);
        log.debug("returned {}", subscription);
        if (subscription == null) {
            throw new NotFoundException(String.format("subscription not found, subscriptionId=%s", subscriptionId));
        }
        return SubscriptionMapper.toSubscriptionDto(subscription);
    }

    /*
     * {@inheritDoc}
     */
     @Override
     public SubscriptionDto saveSubscription(SubscriptionDto subscription) {
         log.debug("save {}", subscription);
         return null;
     }

    public void deleteSubscription(Integer subscriptionId) {
        log.debug("deleted {}", subscriptionId);
    }
}
