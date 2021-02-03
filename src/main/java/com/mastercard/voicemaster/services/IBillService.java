package com.mastercard.voicemaster.services;

import com.mastercard.voicemaster.dto.BillDTO;
import com.mastercard.voicemaster.exception.BillException;
import com.mastercard.voicemaster.models.Bill;

public interface IBillService {

	Bill addBill(BillDTO bill) throws BillException;

}
