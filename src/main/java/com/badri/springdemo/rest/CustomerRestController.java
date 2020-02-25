package com.badri.springdemo.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.badri.springdemo.entity.Customer;
import com.badri.springdemo.service.CustomerService;

@RestController
@RequestMapping("/api") //base url
public class CustomerRestController {

	//autowire the CustomerService as this depends on the CustomerService
	@Autowired
	CustomerService customerService;
	
	//add mapping for get customers GET
	
	//get the list of the customer
	@GetMapping("/customers")
	public List<Customer> getCustomers() {
		
		return customerService.getCustomers();
	}
	
	//get the single customer
	@GetMapping("customers/{customerId}")
	public Customer getCusotmer(@PathVariable int customerId) {
	   
		Customer theCustomer = customerService.getCustomer(customerId);
		if(theCustomer == null) {
			throw new CustomerNotFoundException("Customer not found " + customerId);
		}
		return theCustomer;
		
	}
	
	//add the customer::  POST /customers
	@PostMapping("/customers")
	//user @RequestBody to request the body as POJO
	public Customer addCustomer(@RequestBody Customer theCustomer) {
		
		theCustomer.setId(0); //explicitly set id = 0. it is said to be empty so 
		//hibernate saves it else updates it using saveOrUpdate method.
		
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	//update the customer
	//  PUT for the update 
	@PutMapping("/customers")
	public Customer updateCustomer(@RequestBody Customer theCustomer) {
		
		//just save it hibernate will do the job behind the scenes for updating it
		customerService.saveCustomer(theCustomer);
		
		return theCustomer;
	}
	
	//delete customer
	@DeleteMapping("/customers/{customerId}")
	public String deleteCustomer(@PathVariable int customerId) {
		
		//first get the customer if exist
		Customer customer = customerService.getCustomer(customerId);
		if(customer == null) {
			throw new CustomerNotFoundException("customer not found with id " + customerId);
		}
		
		//else if found delete that customer with associated id
		customerService.deleteCustomer(customerId);
		
		return "Customer deleted  " + customerId;
		
 	}
}
