package com.pwm.ordertracking.exception;

import org.springframework.http.HttpStatus;

public class ErrorItem {
	
    private String message;
    private HttpStatus status;
    
	public ErrorItem(String message, HttpStatus status) {
		super();
		this.message = message;
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	
}

