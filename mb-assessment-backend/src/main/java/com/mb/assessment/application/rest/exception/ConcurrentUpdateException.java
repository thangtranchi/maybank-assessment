package com.mb.assessment.application.rest.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class ConcurrentUpdateException extends RuntimeException {

	public ConcurrentUpdateException(String exception) {
		super(exception);
	}

}
