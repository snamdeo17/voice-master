package com.mastercard.voicemaster.models;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Wallet implements Serializable {

    @Id //to set as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // to set as autoincrement
    private int walletId;

    @OneToMany(mappedBy = "walletHolder")
    private List<Account> accountsInWallet;

    @OneToOne
    private Customer walletOfCustomer;

    private static final long serialVersionUID = 1L;

    public Wallet() {
    }


    public int getWalletId() {
        return walletId;
    }

    public void setWalletId(int walletId) {
        this.walletId = walletId;
    }

    public List<Account> getAccountsInWallet() {
        return accountsInWallet;
    }

    public void setAccountsInWallet(List<Account> accountsInWallet) {
        this.accountsInWallet = accountsInWallet;
        for (Account account : accountsInWallet) {
            account.setWalletHolder(this);
        }
    }

    public Customer getWalletOfCustomer() {
        return walletOfCustomer;
    }

    public void setWalletOfCustomer(Customer walletOfCustomer) {

        this.walletOfCustomer = walletOfCustomer;
    }
}
