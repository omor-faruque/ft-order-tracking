package com.pwm.ordertracking.exception;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;

public class ErrorResponse extends ErrorItem {

private List<String> errors;
	
    public ErrorResponse(Date timestamp, String message, HttpStatus status, List<String> errors) {
		super(timestamp, message, status);
		this.errors = errors;
	}

	public List<String> getErrors() {
		return errors;
	}

	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
