package org.gunnarro.microservice.mymicroservice.repository;

import org.gunnarro.microservice.mymicroservice.repository.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


/**
 * As default spring looking for schema.sql and data.sql
 * Thereafter it is looking for schema-${platform}.sql and data-${platform}.sql, where platform is set by the
 * 'spring.datasource.platform' application property (hsqldb, h2, oracle, mysql, postgresql etc.).
 */
@SqlGroup({
        @Sql(statements = {
                "DROP TABLE IF EXISTS SUBSCRIPTION;",
                "DROP TABLE IF EXISTS CUSTOMER;",
                "DROP TABLE IF EXISTS PERSON;",
                "DROP TABLE IF EXISTS ADDRESS;",
                "DROP TABLE IF EXISTS CUSTOMER_PROFILE;",
                "DROP TABLE IF EXISTS FIELD_TYPE;"}),
        @Sql({"/db/schema-h2.sql", "/db/data-h2.sql"})
})
@DataJpaTest
@TestPropertySource("/test-jpa-application.properties")
// opt uot the auto config of the test in-memory database
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findCustomerById(1L).orElse(null);
        assertNotNull(customer);
        assertEquals(1, customer.getId());
        assertEquals("gunnar", customer.getPerson().getFirstName());
        assertNull(customer.getPerson().getMiddleName());
        assertEquals("ronneberg", customer.getPerson().getLastName());
        customer.getPerson().getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        assertEquals(1107196922222L, customer.getPerson().getSocialSecurityNumber());
        assertNull(customer.getPerson().getAddress());

        assertEquals(2, customer.getSubscriptions().size());
        assertEquals("mobile 4G", customer.getSubscriptions().get(0).getName());
        assertEquals("mobile", customer.getSubscriptions().get(0).getType());
        assertEquals("ACTIVE", customer.getSubscriptions().get(0).getStatus());
        assertNotNull(customer.getSubscriptions().get(0).getStartDate());
        assertNotNull(customer.getSubscriptions().get(0).getEndDate());

        assertEquals("tv package 2", customer.getSubscriptions().get(1).getName());
        assertEquals("tv", customer.getSubscriptions().get(1).getType());
        assertEquals("TERMINATED", customer.getSubscriptions().get(1).getStatus());
        assertNotNull(customer.getSubscriptions().get(1).getStartDate());
        assertNotNull(customer.getSubscriptions().get(1).getEndDate());
    }
}
