package com.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.user.payload.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
    	String message=exception.getMessage();
		ApiResponse obj=new ApiResponse();
		obj.setMessage(message);
		obj.setSuccess(true);
		obj.setHttpStatus(HttpStatus.NOT_FOUND);
    	
    	return new ResponseEntity<ApiResponse>(obj,HttpStatus.NOT_FOUND);
    }
}
