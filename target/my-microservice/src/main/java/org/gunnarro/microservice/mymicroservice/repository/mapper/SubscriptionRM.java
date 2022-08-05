package org.gunnarro.microservice.mymicroservice.repository.mapper;

import org.gunnarro.microservice.mymicroservice.domain.subscription.Subscription;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TODO Remove or Refactor. This class only serves as an example
 */
public class SubscriptionRM implements RowMapper<Subscription> {
    private static final String FIELD_ABONNENT_NR = "abonnent_nr";
    private static final String FIELD_KURT_ID = "kurt_id";

    @Override
    public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Subscription.builder()
                .subscriptionId(rs.getInt(FIELD_ABONNENT_NR))
                .kurtId(rs.getInt(FIELD_KURT_ID))
                .build();
    }
}