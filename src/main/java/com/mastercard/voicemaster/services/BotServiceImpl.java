package com.mastercard.voicemaster.services;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mastercard.voicemaster.dto.RuleDTO;
import com.mastercard.voicemaster.models.Account;
import com.mastercard.voicemaster.models.Bill;
import com.mastercard.voicemaster.models.Customer;
import com.mastercard.voicemaster.models.Wallet;
import com.mastercard.voicemaster.repository.BillRepository;
import com.mastercard.voicemaster.repository.CustomerRepository;

@Service
public class BotServiceImpl implements IBotService {

	@Autowired
	private BillRepository billRepo;

	@Autowired
	private CustomerRepository customerRepo;

	@Autowired
	WalletService walletService;

	@Value("${json.path}")
	private String path;

	@Override
	public String processMessage(String message, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ObjectMapper objectMapper = new ObjectMapper();

		List<RuleDTO> rules = objectMapper.readValue(new File(path), new TypeReference<List<RuleDTO>>() {
		});

		JSONObject obj = new JSONObject();
		for (RuleDTO rule : rules) {
			if (message.matches(rule.getInput())) {
				String userId = request.getHeader("userId");
				if (rule.getAction().equals("INIT")) {
					if (userId != null) {
						String output = rule.getOutput();
						Optional<Customer> userOptional = customerRepo.findById(Integer.parseInt(userId));
						if (userOptional.isPresent()) {
							Customer user = userOptional.get();
							output = output + " " + user.getFname() + " " + user.getLname();
							obj.put("resp", output);
						}
					} else {
						obj.put("resp", rule.getOutput());
					}
					break;
				} else if (rule.getAction().equals("PROVIDECODE")) {
					Pattern p = Pattern.compile("\\d+");
					Matcher m = p.matcher(message);
					while (m.find()) {
						String pin = m.group();
						Customer user = customerRepo.findBySecretCode(pin);
						if (user != null) {
							String output = rule.getOutput();
							output = output.replace("#user#", user.getFname() + " " + user.getLname());
							obj.put("resp", output);
							obj.put("userId", user.getUserId());
							response.setHeader("userId", "" + user.getUserId());
						} else {
							obj.put("resp", "I don't have any matching code in my database. Please provide valid code");
						}
					}
					break;
				}

				if (userId != null) {
					if (rule.getAction().equals("ASK_LIST")) {
						Optional<Customer> userOptional = customerRepo.findById(Integer.parseInt(userId));
						if (userOptional.isPresent()) {
							Customer user = userOptional.get();
							String output = rule.getOutput();
							output = output.replace("#user#", user.getFname() + " " + user.getLname());
							List<Bill> bills = billRepo.findByUserId(user.getUserId());
							if (bills.isEmpty()) {
								output = output + " There is no bill pending for you ";
							} else {
								output = output + " here is the list of pending items \n";
								for (Bill bill : bills) {
									output = output + " " + bill.getName() + " bill with due date " + bill.getDueDate()
											+ " \n";
								}
							}
							obj.put("resp", output);
						} else {
							obj.put("resp", "I don't have any matching code in my database. Please provide valid code");
						}

					} else if (rule.getAction().equals("BILLPAY")) {
						Pattern p = Pattern.compile("my (\\w+) bill");
						Matcher m = p.matcher(message);
						while (m.find()) {
							String billName = m.group(1);
							String res = rule.getOutput();
							Optional<Customer> userOptional = customerRepo.findById(Integer.parseInt(userId));
							if (userOptional.isPresent()) {
								Customer user = userOptional.get();
								Wallet wallet = user.getWallet();
								Account account = wallet.getAccountsInWallet().get(0);
								Float balance = walletService.getAccountBalanceForCurrentWallet(
										user.getWallet().getWalletId(), account.getAccountNumber());
								res = res.replace("#amount#", balance.toString());
								Bill bill = billRepo.findByName(billName);
								if (bill != null && bill.getStatus().equals("PENDING") && balance >= bill.getAmount()) {
									res = res + " and you have enough balance for paying your " + billName
											+ " bill of amount " + bill.getAmount()
											+ " rupees. Should i proceed with the payment?";
									bill.setRequestPayment(true);
									billRepo.save(bill);
								} else if (bill != null && bill.getStatus().equals("PENDING")
										&& balance < bill.getAmount()) {
									res = res + "You don't have enough money in your account for paying " + billName + " bill";
								} else {
									res = res + "There is no pending bill found for " + billName;
								}
								obj.put("resp", res);
							}
						}

					} else if (rule.getAction().equals("PAYCONFIRMATION")) {
						String output = rule.getOutput();
						Optional<Customer> userOptional = customerRepo.findById(Integer.parseInt(userId));
						if (userOptional.isPresent()) {
							Customer user = userOptional.get();
							Wallet wallet = user.getWallet();
							Account account = wallet.getAccountsInWallet().get(0);
							List<Bill> bill = billRepo.findByUserIdAndRequestPayment(Integer.parseInt(userId));
							if (bill.size() > 0) {
								for (Bill bill2 : bill) {
									bill2.setStatus("PAID");
									bill2.setPaidOn(LocalDate.now());
									billRepo.save(bill2);
									output = output + " I have paid the amount of " + bill2.getAmount() + " for your "
											+ bill2.getName() + " bill";
									Account acc = walletService.withdrawFromAccount(wallet.getWalletId(),
											account.getAccountNumber(), bill2.getAmount(), "WITHDRAW");
									output = output + " Now you have " + acc.getBalance() + " in  your account.";
								}
								obj.put("resp", output);
							} else {
								obj.put("resp", "I don't know the context. Sorry");
							}
						}

					} else if (rule.getAction().equals("CURRENTDATE")) {
						obj.put("resp", rule.getOutput().replace("#date#", LocalDate.now().toString()));
					} else {
						obj.put("resp", rule.getOutput());
					}
				} else {
					obj.put("resp", "Kindly authenticate yourself to start. Please Provide your secret code");
				}
				break;
			} else {
				obj.put("resp", "Sorry, I am not trained for this. Kindly ask what i know.");
			}
		}
		return obj.toString();
	}

}
