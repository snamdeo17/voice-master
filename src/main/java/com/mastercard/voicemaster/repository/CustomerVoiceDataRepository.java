package com.mastercard.voicemaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mastercard.voicemaster.models.Customer;
import com.mastercard.voicemaster.models.CustomerVoiceData;

public interface CustomerVoiceDataRepository extends CrudRepository<Customer, Integer> {

	 @Query("SELECT d FROM CustomerVoiceData d WHERE d.customer.userId=:userID")
	 List<CustomerVoiceData> findCustomerVoiceDataByUserID(@Param("userID") Integer userID);


}
