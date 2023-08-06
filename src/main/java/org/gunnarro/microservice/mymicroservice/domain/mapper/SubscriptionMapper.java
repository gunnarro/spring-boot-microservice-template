package org.gunnarro.microservice.mymicroservice.domain.mapper;

import org.gunnarro.microservice.mymicroservice.domain.dto.subscription.SubscriptionDto;
import org.gunnarro.microservice.mymicroservice.repository.entity.Subscription;

public class SubscriptionMapper {

    public static SubscriptionDto toSubscriptionDto(Subscription subscription) {
        return SubscriptionDto.builder()
                .subscriptionId(subscription.getId())
                .name(subscription.getName())
                .build();
    }
}