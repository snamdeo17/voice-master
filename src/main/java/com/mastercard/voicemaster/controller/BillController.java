package com.mastercard.voicemaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mastercard.voicemaster.dto.BillDTO;
import com.mastercard.voicemaster.models.Bill;
import com.mastercard.voicemaster.services.IBillService;

@Controller
public class BillController {

	@Autowired
	private IBillService billService;

	@PostMapping("/api/bill")
	@ResponseBody
	public Bill addBill(@RequestBody BillDTO bill) {
		return billService.addBill(bill);
	}
}
