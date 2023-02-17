package com.ebankdigit.ebankingdigit.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebankdigit.ebankingdigit.entities.BankAccount;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {

}
