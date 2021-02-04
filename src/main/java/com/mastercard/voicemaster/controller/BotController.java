package com.mastercard.voicemaster.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mastercard.voicemaster.services.IBotService;

import io.swagger.annotations.ApiOperation;

@Controller
@CrossOrigin(origins = "*")
public class BotController {

	@Autowired
	IBotService botService;

	@GetMapping("/bot/message")
	@ResponseBody
	public String getMessage(@RequestParam(value = "message") String message, HttpServletRequest request,
			HttpServletResponse response) {

		String resp = null;
		try {
			resp = botService.processMessage(message, request, response);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
	
	@GetMapping("/bot/message/transactionhistory")
	@ApiOperation(value = "This method is used to show transaction history of a user.", hidden = true)
	@ResponseBody
	public JSONObject getTransactionHistory(@RequestParam(value = "message") String message, HttpServletRequest request,
			HttpServletResponse response) throws ParseException {

		JSONObject resp = null;
		try {
			resp = botService.processMessageJSON(message, request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resp;
	}
}
