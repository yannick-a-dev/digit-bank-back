package com.ebankdigit.ebankingdigit.web;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ebankdigit.ebankingdigit.dto.CustomerDTO;
import com.ebankdigit.ebankingdigit.exception.CustomerNotFoundException;
import com.ebankdigit.ebankingdigit.services.BankAccountService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@AllArgsConstructor
@CrossOrigin("*")
@Slf4j
public class CustomerRestController {

	private BankAccountService bankAccountService;
	
	@GetMapping("/customers")
	public List<CustomerDTO> customers(){
		return bankAccountService.listcustomers();
	}
	
	@GetMapping("/customers/{id}")
	public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
		return bankAccountService.getCustomer(customerId);	
	}
	
	@PostMapping("/customers")
	public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO) {
		return bankAccountService.saveCustomer(customerDTO);
	}
	
	@GetMapping("/customers/search")
	public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword", defaultValue = "") String keyword){
		return bankAccountService.searchCustomers(keyword);
	}
	
	@PutMapping("/customers/{customerId}")
	public CustomerDTO updateCustomer(@PathVariable Long customerId,@RequestBody CustomerDTO customerDTO) {
		customerDTO.setId(customerId);
		return bankAccountService.updateCustomer(customerDTO);
	}
	
	@DeleteMapping("/customers/{id}")
	public void deleteCustomer(@PathVariable Long id) {
		bankAccountService.deleteCustomer(id);
	}
}
