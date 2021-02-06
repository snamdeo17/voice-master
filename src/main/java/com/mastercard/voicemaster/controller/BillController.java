package com.mastercard.voicemaster.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mastercard.voicemaster.dto.BillDTO;
import com.mastercard.voicemaster.exception.BillException;
import com.mastercard.voicemaster.models.Bill;
import com.mastercard.voicemaster.models.ServiceResponse;
import com.mastercard.voicemaster.services.IBillService;

@Controller
public class BillController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IBillService billService;

	@PostMapping("/api/bill")
	@ResponseBody
	public ResponseEntity<ServiceResponse> addBill(@RequestBody BillDTO billDTO) {
		LOGGER.debug("Adding bill iformation: {}", billDTO);
		ServiceResponse response = new ServiceResponse();
		try {
			Bill bill = billService.addBill(billDTO);
			response.setStatus("200");
			response.setDescription("Bill created successfully!");
			response.setData(bill);
			LOGGER.debug("Done Adding bill iformation");
			return new ResponseEntity<>(response, HttpStatus.OK);

		} catch (BillException e) {
			response.setStatus(String.valueOf(HttpStatus.EXPECTATION_FAILED));
			response.setDescription(e.getMessage());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
	}
}
