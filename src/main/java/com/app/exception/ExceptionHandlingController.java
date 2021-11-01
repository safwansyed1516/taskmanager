package com.app.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {

	/**
	 * Mapping Exception for particular Exception.
	 *
	 * @param ex exception
	 * @return {@link ResponseEntity}
	 */
	@ExceptionHandler(TaskManagerServiceException.class)
	public ResponseEntity<ExceptionResponse> handleException(final TaskManagerServiceException ex) {
		final ExceptionResponse response = new ExceptionResponse();
		response.setErrorCode(ex.getCode());
		response.setErrorMessage(ex.getMessage());
		return new ResponseEntity<>(response, ex.getHttpCode());
	}

}
