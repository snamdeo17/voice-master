package com.mastercard.voicemaster.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface IBotService {

	String processMessage(String message, HttpServletRequest request, HttpServletResponse response) throws Exception;

}
