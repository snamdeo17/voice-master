package com.mastercard.voicemaster.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mastercard.voicemaster.models.BankTransaction;

public interface BankTansactionRepository extends CrudRepository<BankTransaction, Integer> {
    @Query("SELECT t FROM BankTransaction t WHERE t.transactionId=:transactionId")
    Iterable<BankTransaction> findBankTransactionById(@Param("transactionId") Integer transactionId);
}
