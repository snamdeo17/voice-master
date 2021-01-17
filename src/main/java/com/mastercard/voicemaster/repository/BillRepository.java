package com.mastercard.voicemaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mastercard.voicemaster.models.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and b.status='PENDING'")
	List<Bill> findByUserId(@Param("userId") int userId);

	Bill findByName(String billName);

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and b.name=:billName and b.status='PENDING'")
	Bill findByUserIdAndBillName(@Param("userId") int userId, @Param("billName") String billName);

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and b.status='PENDING' and b.requestPayment=true")
	List<Bill> findByUserIdAndRequestPayment(@Param("userId") int userId);

}
