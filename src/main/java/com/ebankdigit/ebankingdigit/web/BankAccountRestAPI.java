package com.ebankdigit.ebankingdigit.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebankdigit.ebankingdigit.dto.AccountHistoryDTO;
import com.ebankdigit.ebankingdigit.dto.AccountOperationDTO;
import com.ebankdigit.ebankingdigit.dto.BankAccountDTO;
import com.ebankdigit.ebankingdigit.dto.CreditDTO;
import com.ebankdigit.ebankingdigit.dto.DebitDTO;
import com.ebankdigit.ebankingdigit.dto.TransferRequestDTO;
import com.ebankdigit.ebankingdigit.exception.BalanceNotSufficientException;
import com.ebankdigit.ebankingdigit.exception.BankAccountNotFoundException;
import com.ebankdigit.ebankingdigit.services.BankAccountService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class BankAccountRestAPI {

	private BankAccountService bankAccountService;
	
	@GetMapping("/accounts/{accountId}")
	public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
		return bankAccountService.getBankAccount(accountId);
		
	}
	@GetMapping("/accounts")
	public List<BankAccountDTO> listAccounts(){
		return bankAccountService.bankAccountList();	
	}
	
	@GetMapping("/accounts/{accountId}/operations")
	public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
		return bankAccountService.accountHistory(accountId);
	}
	
	@GetMapping("/accounts/{accountId}/pageOperations")
	public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
			                                          @RequestParam(name = "page", defaultValue = "0") int page, 
			                                          @RequestParam(name = "size", defaultValue = "5") int size) throws BankAccountNotFoundException{
		return bankAccountService.getAccountHistory(accountId,page, size);
	}
	
	@PostMapping("/accounts/debit")
	public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
		this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
		return debitDTO;
	}
	
	@PostMapping("/accounts/credit")
	public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException  {
		this.bankAccountService.credit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
		return creditDTO;
		
	}
	
	@PostMapping("/accounts/tranfer")
	public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException{
		this.bankAccountService.transfer(transferRequestDTO.getAccountSource(),transferRequestDTO.getAccountDestination() ,transferRequestDTO.getAmount());
		
	}
	
}
