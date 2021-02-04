package com.mastercard.voicemaster.controller;


import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.voicemaster.models.Customer;
import com.mastercard.voicemaster.models.ServiceResponse;
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
    public ResponseEntity<ServiceResponse> createCustomer(@Valid @RequestBody Customer customer) {   
    	ServiceResponse response = new ServiceResponse();    	    	
    	String email = customer.getEmail(); 
    	String secretCode = customer.getSecretCode().trim();
    	String emailMatcher = "[A-Za-z0-9._%-+]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}";
    	
    	//Check if request body contains user id
    	if(customer.getUserId() !=0) {
    		response.setDescription("user id is not required");
    		response.setStatus(String.valueOf(HttpStatus.NOT_ACCEPTABLE));
    		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    	}    	
    	//Check if secret code is of 4 digit numeric
    	if(secretCode.length() !=4  && !secretCode.matches("\\d{4}")) {    		
    		response.setDescription("Secret code should be 4 digit numeric");
    		response.setStatus(String.valueOf(HttpStatus.NOT_ACCEPTABLE));
    		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    	}
    	//Check if email id exists in system
    	List<Customer> customers = (List<Customer>) customerRepository.findCustomerByEmail(email);
    	if(customers.size() > 0 ) {    		
    		response.setDescription("Email id is already present in system");
    		response.setStatus(String.valueOf(HttpStatus.NOT_ACCEPTABLE));
    		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    	} 
    	//Check if secret code exists in system
    	Customer existingCus = null;
    	existingCus = customerRepository.findBySecretCode(customer.getSecretCode().trim());
    	if(null != existingCus && null != existingCus.getSecretCode() && existingCus.getSecretCode().equals(customer.getSecretCode())) {    		
    		response.setDescription("Given secret code is aleady present in system. Please use different secret code");
    		response.setStatus(String.valueOf(HttpStatus.NOT_ACCEPTABLE));
    		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    	}
    	//Check if email id is in correct format
    	if(!email.matches(emailMatcher) ) {    		
    		response.setDescription("Email id is not in correct format");
    		response.setStatus(String.valueOf(HttpStatus.NOT_ACCEPTABLE));
    		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    	} 
    	
        Customer addedUser = customerRepository.save(customer);   
        response.setStatus(String.valueOf(HttpStatus.OK));
        response.setDescription("User added successfully with userId: "+addedUser.getUserId());        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
	@PutMapping("/api/customer/{userId}")
	public ResponseEntity<ServiceResponse> updateCustomer(@Valid @PathVariable("userId") int id,
			@RequestBody Customer customer) {
		ServiceResponse response = new ServiceResponse();		
    	String secretCode = customer.getSecretCode().trim();
    	
    	//Check if user Id is present
		Optional<Customer> existingCus = customerRepository.findById(id);
		if (!existingCus.isPresent()) {			
			response.setStatus(String.valueOf(HttpStatus.NOT_FOUND));
			response.setDescription("Given user id is not present in system");
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		}		
		//Check if secret code is of 4 digit numeric
    	if(secretCode.length() !=4  && !secretCode.matches("\\d{4}")) {    		
    		response.setDescription("Secret code should be 4 digit numeric");
    		response.setStatus(String.valueOf(HttpStatus.NOT_ACCEPTABLE));
    		return new ResponseEntity<>(response, HttpStatus.NOT_ACCEPTABLE);
    	}
        customer.setUserId(id);
		customerRepository.save(customer);
		response.setStatus(String.valueOf(HttpStatus.OK));
		response.setDescription("User details updated successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

}
