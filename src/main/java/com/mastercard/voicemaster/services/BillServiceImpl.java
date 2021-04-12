package com.mastercard.voicemaster.services;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mastercard.voicemaster.dto.BillDTO;
import com.mastercard.voicemaster.exception.BillException;
import com.mastercard.voicemaster.models.Bill;
import com.mastercard.voicemaster.repository.BillRepository;
import com.mastercard.voicemaster.repository.CustomerRepository;

@Service
public class BillServiceImpl implements IBillService {

	private final Logger LOGGER = LoggerFactory.getLogger(getClass());

	@Autowired
	private BillRepository billRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Override
	public Bill addBill(BillDTO billDto) throws BillException {
		Bill bill = modelMapper.map(billDto, Bill.class);
		bill.setStatus("PENDING");
		bill.setUser(customerRepo.findById(billDto.getUserId()).get());
		Integer billMonth = billDto.getDueDate().getMonth().getValue();
		List<Bill> dbBill = billRepo.findByUserIdAndMonth(billDto.getUserId(), billMonth,  billDto.getConsumerId());
		if (dbBill.size() != 0) {
			String error = "Bill for " + bill.getName() + " already exist for the Date: " + bill.getDueDate()
					+ ", User Id: " + bill.getUser().getUserId() + " and Consumer Id: "+bill.getConsumerId();
			LOGGER.error(error);
			throw new BillException(error);
		}
		return billRepo.save(bill);
	}
}
