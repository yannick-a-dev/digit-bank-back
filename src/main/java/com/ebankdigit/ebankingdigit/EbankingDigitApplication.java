package com.ebankdigit.ebankingdigit;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import com.ebankdigit.ebankingdigit.dto.BankAccountDTO;
import com.ebankdigit.ebankingdigit.dto.CurrentBankAccountDTO;
import com.ebankdigit.ebankingdigit.dto.CustomerDTO;
import com.ebankdigit.ebankingdigit.dto.SavingBankAccountDTO;
import com.ebankdigit.ebankingdigit.entities.AccountOperation;
import com.ebankdigit.ebankingdigit.entities.BankAccount;
import com.ebankdigit.ebankingdigit.entities.CurrentAccount;
import com.ebankdigit.ebankingdigit.entities.Customer;
import com.ebankdigit.ebankingdigit.entities.SavingAccount;
import com.ebankdigit.ebankingdigit.enums.AccountStatus;
import com.ebankdigit.ebankingdigit.enums.OperationType;
import com.ebankdigit.ebankingdigit.exception.BalanceNotSufficientException;
import com.ebankdigit.ebankingdigit.exception.BankAccountNotFoundException;
import com.ebankdigit.ebankingdigit.exception.CustomerNotFoundException;
import com.ebankdigit.ebankingdigit.repository.AccountOperationRepository;
import com.ebankdigit.ebankingdigit.repository.BankAccountRepository;
import com.ebankdigit.ebankingdigit.repository.CustomerRepository;
import com.ebankdigit.ebankingdigit.services.BankAccountService;

@SpringBootApplication
public class EbankingDigitApplication {

	public static void main(String[] args) {
		SpringApplication.run(EbankingDigitApplication.class, args);
	}
	
	@Bean
	CommandLineRunner commandLineRunner(BankAccountService bankAccountService) {
		return args -> {
		   Stream.of("Raphael", "Yannick", "Francois").forEach(name -> {
			   CustomerDTO customerDTO = new CustomerDTO();
			   customerDTO.setName(name);
			   customerDTO.setEmail(name+"@gmail.com");
			   bankAccountService.saveCustomer(customerDTO);
		   });
		   bankAccountService.listcustomers().forEach(customer -> {
			  try {
				bankAccountService.saveCurrentBankAccount(Math.random()*90000, 90000, customer.getId());
			    bankAccountService.saveSavingBankAccount(Math.random(), 5.5,customer.getId());
			   
			  } catch (CustomerNotFoundException e) {
				e.printStackTrace();
			} 
		   });
		   List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
		    for(BankAccountDTO bankAccount: bankAccounts) {
		    	for(int i = 0; i < 10; i++) {
		    		String accountId;
		    		if(bankAccount instanceof SavingBankAccountDTO) {
		    		  accountId	= ((SavingBankAccountDTO) bankAccount).getId();
		    		}else {
		    		  accountId = ((CurrentBankAccountDTO) bankAccount).getId();
		    		}
		    		bankAccountService.credit(accountId, 10000+Math.random()*120000, "Credit");
		    		bankAccountService.debit(accountId, 1000+Math.random()*9000, "Debit");
		    	}
		    }
		};
	}

	//@Bean
	CommandLineRunner start(CustomerRepository customerRepository, BankAccountRepository bankAccountRepository,
			AccountOperationRepository accountOperationRepository) {
		return args -> {
			Stream.of("Yannick", "Fanfan", "Simon").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});
			customerRepository.findAll().forEach(cust -> {
				CurrentAccount currentAccount = new CurrentAccount();
				// UUID permet de generer manuellement l'id
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 90000);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(9000);
				bankAccountRepository.save(currentAccount);

				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 90000);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterestedRate(5.5);
				bankAccountRepository.save(savingAccount);
			});
			bankAccountRepository.findAll().forEach(acc -> {
				for (int i = 0; i < 10; i++) {
					AccountOperation accountOperation = new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random() * 12000);
					accountOperation.setType(Math.random() > 0.5 ? OperationType.DEBIT : OperationType.CREDIT);
					accountOperation.setBankAccount(acc);
					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}

}
