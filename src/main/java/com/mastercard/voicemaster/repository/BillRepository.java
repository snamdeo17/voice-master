package com.mastercard.voicemaster.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.mastercard.voicemaster.models.Bill;

public interface BillRepository extends JpaRepository<Bill, Long> {

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and b.status='PENDING'")
	List<Bill> findByUserId(@Param("userId") int userId);

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and b.status='PAID'")
	List<Bill> findTransactionHistoryByUserId(@Param("userId") int userId);
	
	Bill findByName(String billName);

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and b.status='PENDING' and b.requestPayment=true")
	List<Bill> findByUserIdAndRequestPayment(@Param("userId") int userId);

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and MONTH(b.dueDate) = :month and b.name=:billName")
	List<Bill> findByUserIdAndMonth(@Param("userId") Integer userId, @Param("month") Integer month, @Param("billName") String billName);

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and b.name=:billName and b.consumerId=:consumerId and b.status='PENDING'")
	Bill findByUserIdAndBillNameAndConsumerId(@Param("userId")int userId, @Param("billName")String billName, @Param("consumerId")Integer consumerId);

	@Query("SELECT b FROM Bill b WHERE b.user.userId=:userId and b.name=:billName and b.status='PENDING'")
	List<Bill> findByUserIdAndBillName(@Param("userId") int userId, @Param("billName") String billName);

}
