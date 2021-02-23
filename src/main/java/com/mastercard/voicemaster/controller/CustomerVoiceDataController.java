package com.mastercard.voicemaster.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.voicemaster.models.CustomerVoiceData;
import com.mastercard.voicemaster.models.ServiceResponse;
import com.mastercard.voicemaster.repository.CustomerVoiceDataRepository;

@RestController
@CrossOrigin(origins = "*")
public class CustomerVoiceDataController {

	@Autowired
	CustomerVoiceDataRepository customerVoiceDataRepository;

	@GetMapping("/api/customervoicedata/{userId}")
	public ResponseEntity<ServiceResponse> findCustomerVoiceDataByUserID(@PathVariable("userId") int userId) {
		ServiceResponse response = new ServiceResponse();
		List<CustomerVoiceData> voiceData = customerVoiceDataRepository.findCustomerVoiceDataByUserID(userId);
		if (userId == 0) {
			response.setDescription("user id is required");
			response.setStatus(String.valueOf(HttpStatus.BAD_REQUEST));
			return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
		}
		response.setData(voiceData);
		response.setStatus(String.valueOf(HttpStatus.OK));
		response.setDescription("User voice samples fetched successfully.");
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

}
