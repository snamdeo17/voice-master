package com.mastercard.voicemaster.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mastercard.voicemaster.models.Wallet;

public interface WalletRepository extends CrudRepository<Wallet, Integer> {
    @Query("SELECT w FROM Wallet  w WHERE w.walletId=:walletId")
    Iterable<Wallet> findWalletById(@Param("walletId") Integer walletId);
}
