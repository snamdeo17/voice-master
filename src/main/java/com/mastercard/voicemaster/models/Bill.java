package com.mastercard.voicemaster.models;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
// @Table(uniqueConstraints={@UniqueConstraint(columnNames = {"user_user_id" ,
// "name" , "status","amount"})})
public class Bill implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private Customer user;

	private String name;
	private Float amount;
	private String status;
	private LocalDate dueDate;
	private LocalDate paidOn;
	private Boolean requestPayment;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Customer getUser() {
		return user;
	}

	public void setUser(Customer user) {
		this.user = user;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	public LocalDate getPaidOn() {
		return paidOn;
	}

	public void setPaidOn(LocalDate paidOn) {
		this.paidOn = paidOn;
	}

	public Boolean getRequestPayment() {
		return requestPayment;
	}

	public void setRequestPayment(Boolean requestPayment) {
		this.requestPayment = requestPayment;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}
}
