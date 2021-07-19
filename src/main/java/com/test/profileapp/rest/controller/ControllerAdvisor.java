package com.test.profileapp.rest.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.web.server.WebServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.test.profileapp.exception.ResourceNotFoundException;
import com.test.profileapp.exception.WebServiceException;
import com.test.profileapp.rest.model.Error;

@ControllerAdvice
public class ControllerAdvisor {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return new ResponseEntity<>(new Error(ex.getErrorMessage(), ex.getErrorCode()), HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
		List<Error> errors = new ArrayList<>();
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorMessage = error.getDefaultMessage();
			errors.add(new Error(errorMessage, null));
		});
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(WebServiceException.class)
	public ResponseEntity<Object> handleWebServiceExceptions(WebServiceException ex) {
		return new ResponseEntity<>(new Error(ex.getErrorMessage(), ex.getErrorCode()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
