package com.mastercard.voicemaster.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.voicemaster.exception.CustomerAlreadyHasWalletException;
import com.mastercard.voicemaster.exception.CustomerDoesNotExistException;
import com.mastercard.voicemaster.exception.VoiceMasterException;
import com.mastercard.voicemaster.models.Account;
import com.mastercard.voicemaster.models.Customer;
import com.mastercard.voicemaster.models.ServiceResponse;
import com.mastercard.voicemaster.repository.AccountRepository;
import com.mastercard.voicemaster.repository.CustomerRepository;
import com.mastercard.voicemaster.services.IAccountService;

@RestController
public class AccountController {

	@Autowired
	AccountRepository accountRepository;
	
	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	IAccountService accService;

	@PostMapping("/api/account")
	public ResponseEntity<ServiceResponse> createAccount(@RequestBody Account account) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException, VoiceMasterException {
		 ServiceResponse response = new ServiceResponse();
			Account raccount = accService.createAccount(account);
			response.setStatus("200");
            response.setDescription("Account created successfully!");
            response.setData(raccount);
            return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/api/account/balance/{userId}")
	public String getAccontBalance(@PathVariable("userId") int userId) {
		Optional<Customer> userOptional = customerRepo.findById(userId);
		Float balance = (float) 0;
		if (userOptional.isPresent()) {
			Customer user = userOptional.get();
			List<Account> accounts = user.getCustomerAccounts();
			for (Account acnt : accounts) {
				balance = balance + acnt.getBalance();
			}
		}
		return balance.toString();
	}

}
