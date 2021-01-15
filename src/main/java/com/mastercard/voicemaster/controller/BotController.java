package com.mastercard.voicemaster.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mastercard.voicemaster.services.IBotService;

@Controller
@CrossOrigin(origins = "*")
public class BotController {

	@Autowired
	IBotService botService;

	@GetMapping("/bot/message")
	@ResponseBody
	public String getMessage(@RequestParam(value = "message") String message) {

		String resp = null;
		try {
			resp = botService.processMessage(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resp;
	}
}
