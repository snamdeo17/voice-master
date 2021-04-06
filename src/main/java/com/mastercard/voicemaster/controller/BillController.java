package com.mastercard.voicemaster.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mastercard.voicemaster.dto.BillDTO;
import com.mastercard.voicemaster.exception.BillException;
import com.mastercard.voicemaster.models.Bill;
import com.mastercard.voicemaster.models.ConsumerUserMap;
import com.mastercard.voicemaster.models.Customer;
import com.mastercard.voicemaster.models.ServiceResponse;
import com.mastercard.voicemaster.repository.BillRepository;
import com.mastercard.voicemaster.repository.ConsumerUserMapRepository;
import com.mastercard.voicemaster.services.IBillService;

@RestController
@CrossOrigin(origins = "*")
@Controller
public class BillController {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private IBillService billService;
	
	@Autowired
	BillRepository billRepository;
	
	@Autowired
	ConsumerUserMapRepository consumerUserMapRepository;

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
	
	
	@GetMapping("/api/bill")
    public List<Bill> findAllBills() {
        return (List<Bill>)billRepository.findAll();
    }
	
	
	@GetMapping("/api/bill/{userId}")
    public List<Bill> findCustomerByIdForAllBills(@PathVariable("userId") int id) {

        return billRepository.findByUserIdForAllBills(id);
    }
	
	
	@PostMapping("/api/bill/createConsumerUserMap")
    public ResponseEntity<ServiceResponse> createConsumerUserMapRecord(@RequestBody ConsumerUserMap consumerUserMap) {
		ServiceResponse response = new ServiceResponse();
		System.out.println("UserId and Type: "+consumerUserMap.getUserId()+" : "+ consumerUserMap.getType());
		String consumerId = consumerUserMapRepository.findCustomerIdByUserIdAndType(consumerUserMap.getType());
		ConsumerUserMap consumerUserMapSave = new ConsumerUserMap();
		
		consumerUserMapSave.setUserId(consumerUserMap.getUserId());
		consumerUserMapSave.setType(consumerUserMap.getType());
		
		if(null==consumerId) {
			if(consumerUserMap.getType().equalsIgnoreCase("Water")) {
				consumerUserMapSave.setConsumerId(1000);
			} else if(consumerUserMap.getType().equalsIgnoreCase("Electricity")) {
				consumerUserMapSave.setConsumerId(5000);
			} else if(consumerUserMap.getType().equalsIgnoreCase("Gas")) {
				consumerUserMapSave.setConsumerId(10000); 
			}
		} else {
			int consumId = Integer.parseInt(consumerId)+1;
			consumerUserMapSave.setConsumerId(consumId);
		}
		
		consumerUserMapRepository.save(consumerUserMapSave);
		
		response.setStatus(String.valueOf(HttpStatus.OK));
		response.setDescription("New Consumer ID Added successfully");
		return new ResponseEntity<>(response, HttpStatus.OK);
		
	}
	
	
	@GetMapping("/api/bill/createConsumerUserMap/{userId}")
    public List<ConsumerUserMap> getAllConsumerDetailsById(@PathVariable("userId") int id) {

		List<ConsumerUserMap> cosumerMapListObj = consumerUserMapRepository.findByUserId(id);
		return cosumerMapListObj;
    }
	
	
}
