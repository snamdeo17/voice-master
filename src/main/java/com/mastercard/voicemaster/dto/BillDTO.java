package com.mastercard.voicemaster.dto;

import java.time.LocalDate;

public class BillDTO {
	private Long id;

	private Integer userId;

	private String name;
	private Float amount;
	private String status;
	private LocalDate dueDate;
	private LocalDate paidOn;
	private Boolean requestPayment;
	private Integer consumerId;
	
	public Integer getConsumerId() {
		return consumerId;
	}

	public void setConsumerId(Integer consumerId) {
		this.consumerId = consumerId;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Float getAmount() {
		return amount;
	}
	public void setAmount(Float amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	@Override
	public String toString() {
		return "BillDTO [id=" + id + ", userId=" + userId + ", name=" + name + ", amount=" + amount + ", status="
				+ status + ", dueDate=" + dueDate + ", paidOn=" + paidOn + "]";
	}
}
