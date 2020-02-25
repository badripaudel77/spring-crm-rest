package com.badri.springdemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

//global exception handling
@ControllerAdvice
public class CustomerRestExceptionHandler {

	//exception for Customer not found
	@ExceptionHandler
	public ResponseEntity<CustomerErrorResponse> handleException(CustomerNotFoundException e) {

		CustomerErrorResponse error = new CustomerErrorResponse();
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(e.getMessage());
		error.setTimeStamp(System.currentTimeMillis());

		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
	}
	
	       //add exception for all the other exception (catch all )
	       //catch all
	
			@ExceptionHandler
			public ResponseEntity<CustomerErrorResponse> handleException(Exception e) {

				CustomerErrorResponse error = new CustomerErrorResponse();
				
				error.setStatus(HttpStatus.BAD_REQUEST.value());
				error.setMessage(e.getMessage());
				error.setTimeStamp(System.currentTimeMillis());

				
				return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
			}
}
