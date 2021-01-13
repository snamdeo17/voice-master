package com.mastercard.voicemaster.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.voicemaster.models.Customer;
import com.mastercard.voicemaster.repository.CustomerRepository;

@RestController
public class CustomerController {

    @Autowired
    CustomerRepository customerRepository;

    @GetMapping("/api/customer")
    public List<Customer> findAllCustomers(
            @RequestParam(name="email",required=false) String email) {

        if (email!=null) {
            return (List<Customer>)customerRepository.findCustomerByEmail(email);
        }

        return (List<Customer>)customerRepository.findAll();
    }

    @GetMapping("/api/customer/{userId}")
    public Customer findCustomerById(@PathVariable("userId") int id) {

        return customerRepository.findById(id).orElse(null);
    }


    @PostMapping("/api/customer")
    public Customer createCustomer(@RequestBody Customer customer) {

        return customerRepository.save(customer);
    }

}
