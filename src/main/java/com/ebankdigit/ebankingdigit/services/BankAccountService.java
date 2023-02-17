package com.ebankdigit.ebankingdigit.services;

import java.util.List;

import com.ebankdigit.ebankingdigit.dto.AccountHistoryDTO;
import com.ebankdigit.ebankingdigit.dto.AccountOperationDTO;
import com.ebankdigit.ebankingdigit.dto.BankAccountDTO;
import com.ebankdigit.ebankingdigit.dto.CurrentBankAccountDTO;
import com.ebankdigit.ebankingdigit.dto.CustomerDTO;
import com.ebankdigit.ebankingdigit.dto.SavingBankAccountDTO;
import com.ebankdigit.ebankingdigit.entities.BankAccount;
import com.ebankdigit.ebankingdigit.entities.CurrentAccount;
import com.ebankdigit.ebankingdigit.entities.SavingAccount;
import com.ebankdigit.ebankingdigit.exception.BalanceNotSufficientException;
import com.ebankdigit.ebankingdigit.exception.BankAccountNotFoundException;
import com.ebankdigit.ebankingdigit.exception.CustomerNotFoundException;

public interface BankAccountService {

	CustomerDTO saveCustomer(CustomerDTO customerDTO);
	
	CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance,double overDraft, Long customerId) throws CustomerNotFoundException;
	
	SavingBankAccountDTO saveSavingBankAccount(double initialBalance,double interestRate, Long customerId) throws CustomerNotFoundException;
	
	List<CustomerDTO> listcustomers();
	
	BankAccountDTO getBankAccount(String accoundId) throws BankAccountNotFoundException;
	
	void debit(String accoundId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
	
	void credit(String accoundId, double amount, String description) throws BankAccountNotFoundException;
	
	void transfer(String accoundIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;

	List<BankAccountDTO> bankAccountList();

	CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

	CustomerDTO updateCustomer(CustomerDTO customerDTO);

	void deleteCustomer(Long id);

	List<AccountOperationDTO> accountHistory(String accountId);

	AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

	List<CustomerDTO> searchCustomers(String keyword);
}
