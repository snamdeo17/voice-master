package com.mastercard.voicemaster.services;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.voicemaster.dto.RuleDTO;
import com.mastercard.voicemaster.models.Bill;
import com.mastercard.voicemaster.repository.BillRepository;

@Service
public class BotServiceImpl implements IBotService {

	@Autowired
	private BillRepository billRepo;

	@Value("${json.path:D:\\dev\\ibaseWorkspace\\Voice-Master\\src\\main\\resources\\rules.json}")
	private String path;

	@Override
	public String processMessage(String message) throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		// read json file and convert to customer object
		List<RuleDTO> rules = objectMapper.readValue(new File(path), new TypeReference<List<RuleDTO>>() {
		});
		List<String> rulesList = new ArrayList<String>();
		rules.stream().forEach(rule -> {
			rulesList.add(rule.getGrammer());
		});
		JSONObject obj = new JSONObject();
		if (message.equalsIgnoreCase("hello")) {
			obj.put("resp", "How can i help you?");
		} else if (message.equalsIgnoreCase("please pay my bills")) {
			obj.put("resp", "please authenticate yourself. Provide your secret code");
		} else if (message.contains("my code is")) {
			Pattern p = Pattern.compile("\\d+");
			Matcher m = p.matcher(message);
			while (m.find()) {
				String pin = m.group();
				if (pin.equals("123")) {
					Iterable<Bill> bills = billRepo.findAll();
					String billStr = "";
					for (Bill bill : bills) {
						billStr = billStr + bill.getName() + " \n";
					}
					obj.put("resp", "thank you shivendra. here is the list of pending items " + billStr);
				} else {
					obj.put("resp", "I don't have any matching code in my database. Please provicde valid code");
				}
			}
		} else {
			obj.put("resp", "please say again. i didn't get");
		}
		return obj.toString();
	}

}
