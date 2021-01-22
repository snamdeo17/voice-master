package com.mastercard.voicemaster.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

public interface IBotService {

	String processMessage(String message, HttpServletRequest request, HttpServletResponse response) throws Exception;

	JSONObject processMessageJSON(String message, HttpServletRequest request, HttpServletResponse response)
			throws Exception;

}
