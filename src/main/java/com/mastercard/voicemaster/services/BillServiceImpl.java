package com.mastercard.voicemaster.services;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mastercard.voicemaster.dto.BillDTO;
import com.mastercard.voicemaster.models.Bill;
import com.mastercard.voicemaster.repository.BillRepository;
import com.mastercard.voicemaster.repository.CustomerRepository;

@Service
public class BillServiceImpl implements IBillService {

	@Autowired
	private BillRepository billRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Bill addBill(BillDTO billDto) {
		Bill bill = modelMapper.map(billDto, Bill.class);
		bill.setStatus("PENDING");
		bill.setUser(customerRepo.findById(billDto.getUserId()).get());
		return billRepo.save(bill);
	}

}
