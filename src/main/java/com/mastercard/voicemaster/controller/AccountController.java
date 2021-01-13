package com.mastercard.voicemaster.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.voicemaster.models.Account;
import com.mastercard.voicemaster.repository.AccountRepository;
import com.mastercard.voicemaster.repository.CustomerRepository;

@RestController
public class AccountController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CustomerRepository customerRepository;

    @PostMapping("/api/account")
    public Account createAccount(@RequestBody Account account) {
        return accountRepository.save(account);
    }

}
