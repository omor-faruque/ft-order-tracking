package com.pwm.ordertracking.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex) {
		List<String> errors = new ArrayList<String>();
		for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
			String message = violation.getMessage();
			Object invalidValue = violation.getInvalidValue();
			Object rootBean = violation.getRootBean();
			String propertyPath = violation.getPropertyPath().toString();

			logger.error("\nValidation failed for " + rootBean + " with invalid value " + invalidValue + "\n");

			errors.add(propertyPath + ": " + message);
		}

		ErrorResponse er = new ErrorResponse(new Date(), "Validation failed", HttpStatus.BAD_REQUEST, errors);

		return new ResponseEntity<>(er, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorItem> handleDuplicateEntryException(DataIntegrityViolationException ex) {
        String errorMessage = "Duplicate entry. Please provide unique values.";
        ErrorItem errorItem = new ErrorItem(errorMessage, HttpStatus.CONFLICT);
        return new ResponseEntity<>(errorItem, HttpStatus.CONFLICT);
    }
	
	@ExceptionHandler(NoSuchElementException.class)
	public ResponseEntity<ErrorItem> handleNoSuchElementException(NoSuchElementException ex) {
		ErrorItem errorItem = new ErrorItem(ex.getMessage(), HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(errorItem, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<ErrorItem> handleIllegalArgumentException(IllegalArgumentException ex) {
		ErrorItem errorItem = new ErrorItem(ex.getMessage(), HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(errorItem, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorItem> globalExceptionHandler(Exception ex) {
		ErrorItem errorItem = new ErrorItem(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<>(errorItem, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
