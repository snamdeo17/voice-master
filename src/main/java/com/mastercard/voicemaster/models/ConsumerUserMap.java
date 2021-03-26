package com.mastercard.voicemaster.models;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import io.swagger.annotations.ApiModelProperty;

@Entity
public class ConsumerUserMap  implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	
	private int userId;
	@Id
	private int consumerId;
	private String type;
	
	public ConsumerUserMap() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ConsumerUserMap(int userId, int consumerId, String type) {
		super();
		this.userId = userId;
		this.consumerId = consumerId;
		this.type = type;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getConsumerId() {
		return consumerId;
	}
	public void setConsumerId(int consumerId) {
		this.consumerId = consumerId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	
	

}
