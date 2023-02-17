package com.ebankdigit.ebankingdigit.repository;

import java.util.List;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ebankdigit.ebankingdigit.entities.AccountOperation;

public interface AccountOperationRepository extends JpaRepository<AccountOperation, Long> {

	List<AccountOperation> findByBankAccountId(String accoundId);
	
	//pagination
	Page<AccountOperation> findByBankAccountIdOrderByOperationDateDesc(String accoundId,PageRequest pageRequest);
}
