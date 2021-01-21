package com.mastercard.voicemaster.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mastercard.voicemaster.exception.CustomerAlreadyHasWalletException;
import com.mastercard.voicemaster.exception.CustomerDoesNotExistException;
import com.mastercard.voicemaster.models.Account;
import com.mastercard.voicemaster.models.Customer;
import com.mastercard.voicemaster.models.Wallet;
import com.mastercard.voicemaster.repository.AccountRepository;
import com.mastercard.voicemaster.repository.CustomerRepository;
import com.mastercard.voicemaster.repository.WalletRepository;

@Service
public class AccountServiceImpl implements IAccountService {
	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	WalletRepository walletRepository;

	@Autowired
	WalletService walletService;

	@Override
	public Account createAccount(Account account)
			throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException {
		Customer customer = customerRepository.findById(account.getAccountHolder().getUserId()).get();
		account.setAccountHolder(customer);
		Wallet wallet = walletRepository.findByUserId(account.getAccountHolder().getUserId());
		if (wallet == null) {
			wallet = walletService.createWallet(account.getAccountHolder().getUserId());
		}
		account.setWalletHolder(wallet);
		Account dbAccount = accountRepository.save(account);
		return dbAccount;
	}
}
