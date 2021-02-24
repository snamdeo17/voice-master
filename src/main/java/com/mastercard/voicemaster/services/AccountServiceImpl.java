package com.mastercard.voicemaster.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.mastercard.voicemaster.exception.CustomerAlreadyHasWalletException;
import com.mastercard.voicemaster.exception.CustomerDoesNotExistException;
import com.mastercard.voicemaster.exception.VoiceMasterException;
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
			throws VoiceMasterException {
		
		Account tAccount = accountRepository.findAccountByUserID(account.getAccountHolder().getUserId());
		if (null != tAccount) {
			throw new VoiceMasterException(HttpStatus.EXPECTATION_FAILED,"Account is already associated with User");
		}
		Optional<Customer> opCustomer = customerRepository.findById(account.getAccountHolder().getUserId());
		if(null == opCustomer || opCustomer.isEmpty()) {
			throw new VoiceMasterException(HttpStatus.NOT_FOUND, "Customer with customer ID: "+account.getAccountHolder().getUserId()+" does not exist");
		}
		account.setAccountHolder(opCustomer.get());
		Wallet wallet = walletRepository.findByUserId(account.getAccountHolder().getUserId());
		if (wallet == null) {
			wallet = walletService.createWallet(account.getAccountHolder().getUserId());
		}
		account.setWalletHolder(wallet);
		Account dbAccount = accountRepository.save(account);
		return dbAccount;
	}
}
