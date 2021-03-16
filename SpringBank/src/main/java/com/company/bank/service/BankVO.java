package com.company.bank.service;

import lombok.Data;

@Data
public class BankVO {
	private String bank_name;
	private String account_alias;
	private String account_num_masked;
	private String fintech_use_num;
	private String access_token;
	private String product_name;
	
	
}
