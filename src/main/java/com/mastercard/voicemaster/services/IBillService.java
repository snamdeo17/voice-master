package com.mastercard.voicemaster.services;

import com.mastercard.voicemaster.dto.BillDTO;
import com.mastercard.voicemaster.models.Bill;

public interface IBillService {

	Bill addBill(BillDTO bill);

}
