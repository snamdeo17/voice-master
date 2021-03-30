package com.mastercard.voicemaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.mastercard.voicemaster.models.Bill;
import com.mastercard.voicemaster.models.ConsumerUserMap;

public interface ConsumerUserMapRepository extends CrudRepository<ConsumerUserMap, Integer> {
	
	@Query("SELECT MAX(c.consumerId) FROM ConsumerUserMap c WHERE c.type=:type")
    String findCustomerIdByUserIdAndType(@Param ("type") String type);
	
	@Query("SELECT c FROM ConsumerUserMap c WHERE c.userId=:userId")
	List<ConsumerUserMap> findByUserId(@Param("userId") int userId);

	
	/*
	 * @Query("SELECT MAX(c.consumerId) FROM ConsumerUserMap c WHERE c.userId=:userId and c.type=:type")
    String findCustomerIdByUserIdAndType(@Param("userId") int userId, @Param ("type") String type);

	 */
}
