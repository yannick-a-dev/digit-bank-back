package com.ebankdigit.ebankingdigit.services;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ebankdigit.ebankingdigit.entities.BankAccount;
import com.ebankdigit.ebankingdigit.entities.CurrentAccount;
import com.ebankdigit.ebankingdigit.entities.SavingAccount;
import com.ebankdigit.ebankingdigit.repository.BankAccountRepository;

@Service
@Transactional
public class BankService {
	@Autowired
	private BankAccountRepository bankAccountRepository;

	public void consulter() {
		BankAccount bankAccount = bankAccountRepository.findById("1803e930-ce83-4bfc-8c69-ee30b387882a")
				.orElse(null);
		if (bankAccount != null) {
			System.out.println("********************");
			System.out.println(bankAccount.getId());
			System.out.println(bankAccount.getBalance());
			System.out.println(bankAccount.getStatus());
			System.out.println(bankAccount.getCreatedAt());
			System.out.println(bankAccount.getCustomer().getName());
			System.out.println(bankAccount.getClass().getSimpleName());
			if (bankAccount instanceof CurrentAccount) {
				System.out.println("Over Draft=>" + ((CurrentAccount) bankAccount).getOverDraft());
			} else {
				System.out.println("Rate=>" + ((SavingAccount) bankAccount).getInterestedRate());
			}

			bankAccount.getAccountOperations().forEach(op -> {
				System.out.println("=================");
				System.out.println(op.getType());
				System.out.println(op.getOperationDate());
				System.out.println(op.getAmount());
			});
		}
	}
}
