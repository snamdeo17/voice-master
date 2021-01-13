package com.mastercard.voicemaster.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mastercard.voicemaster.models.Customer;

public interface CustomerRepository extends CrudRepository<Customer, Integer> {

    @Query("SELECT c FROM Customer c WHERE c.email=:email")
    Iterable<Customer> findCustomerByEmail(@Param("email") String email);


}
