package com.mastercard.voicemaster.exception;

public class AccountNotAssociatedWithWalletException extends Exception {

    public AccountNotAssociatedWithWalletException(int walletId, int accountId) {
        super("Wallet with walledId : "+walletId+" is not associated with accountId : "+accountId);
    }
}
