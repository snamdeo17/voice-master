package com.mastercard.voicemaster.dto;

import java.util.List;

public class RuleDTO {
	public String action;
	public String input;
	public String output;
	private List<String> dictonary;

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public List<String> getDictonary() {
		return dictonary;
	}

	public void setDictonary(List<String> dictonary) {
		this.dictonary = dictonary;
	}
}
