package com.ebankdigit.ebankingdigit.dto;

import lombok.Data;

@Data
public class DebitDTO {

	private String accountId;
	
	private double amount;
	
	private String description;
}
