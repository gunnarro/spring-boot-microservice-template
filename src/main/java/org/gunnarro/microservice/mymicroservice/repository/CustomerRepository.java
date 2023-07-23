package org.gunnarro.microservice.mymicroservice.repository;


import org.gunnarro.microservice.mymicroservice.repository.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE c.id = :customerId")
    Optional<Customer> findCustomerById(@Param("customerId") Long customerId);
}