package com.ebankdigit.ebankingdigit.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ebankdigit.ebankingdigit.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

	List<Customer> findByNameContains(String keyword);

}
