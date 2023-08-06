package org.gunnarro.microservice.mymicroservice.repository.mapper;

import org.gunnarro.microservice.mymicroservice.repository.entity.Subscription;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * TODO Remove or Refactor. This class only serves as an example
 */
public class SubscriptionRM implements RowMapper<Subscription> {
    private static final String FIELD_SUBSCRIPTION_ID = "subscription_id";
    private static final String FIELD_SUBSCRIPTION_NAME = "name";
    private static final String FIELD_SUBSCRIPTION_TYPE = "type";
    private static final String FIELD_SUBSCRIPTION_STATUS = "status";
   

    @Override
    public Subscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Subscription.builder()
                .id(rs.getLong(FIELD_SUBSCRIPTION_ID))
                .name(rs.getString(FIELD_SUBSCRIPTION_NAME))
                .type(rs.getString(FIELD_SUBSCRIPTION_TYPE))
                .status(rs.getString(FIELD_SUBSCRIPTION_STATUS))
                .build();
    }
}
