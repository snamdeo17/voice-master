package com.mastercard.voicemaster.exception;

import com.mastercard.voicemaster.models.Customer;
import com.mastercard.voicemaster.models.Wallet;

public class WalletDoesNotBelongToCustomer extends Exception {
    public WalletDoesNotBelongToCustomer(Customer customer, Wallet wallet) {
        super("Customer with id"+customer.getUserId()+" does not have associated walletId : "+wallet.getWalletId());
    }
}
