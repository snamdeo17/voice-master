package com.mastercard.voicemaster.services;

import com.mastercard.voicemaster.exception.CustomerAlreadyHasWalletException;
import com.mastercard.voicemaster.exception.CustomerDoesNotExistException;
import com.mastercard.voicemaster.models.Account;

public interface IAccountService {

	Account createAccount(Account account) throws CustomerDoesNotExistException, CustomerAlreadyHasWalletException ;

}
