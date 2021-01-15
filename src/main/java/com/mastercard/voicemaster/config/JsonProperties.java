package com.mastercard.voicemaster.config;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import com.mastercard.voicemaster.dto.RuleDTO;

@Component
@PropertySource(value = "classpath:rules.json")
@ConfigurationProperties
public class JsonProperties {
	List<RuleDTO> rules;

	public List<RuleDTO> getRules() {
		return rules;
	}

	public void setRules(List<RuleDTO> rules) {
		this.rules = rules;
	}
}
