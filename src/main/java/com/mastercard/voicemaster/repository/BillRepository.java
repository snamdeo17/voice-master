package com.mastercard.voicemaster.repository;

import org.springframework.data.repository.CrudRepository;

import com.mastercard.voicemaster.models.Bill;

public interface BillRepository extends CrudRepository<Bill, Long> {

}
