package com.mastercard.voicemaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.voicemaster.models.Account;
import com.mastercard.voicemaster.models.ServiceResponse;
import com.mastercard.voicemaster.repository.AccountRepository;
import com.mastercard.voicemaster.services.IAccountService;

@RestController
public class AccountController {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	IAccountService accService;

	@PostMapping("/api/account")
	public ResponseEntity<ServiceResponse> createAccount(@RequestBody Account account) {
		 ServiceResponse response = new ServiceResponse();
		try {
			Account raccount = accService.createAccount(account);
			response.setStatus("200");
            response.setDescription("Account created successfully!");
            response.setData(raccount);
            return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
            response.setDescription(e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
	}

}
