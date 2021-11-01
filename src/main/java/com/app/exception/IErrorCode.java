package com.app.exception;

import org.springframework.http.HttpStatus;

/**
 * 
 *
 * @author safwan
 *
 */
public interface IErrorCode {

	/**
	 * Returns the unique code identifying the cause of this error
	 */
	Integer getCode();

	HttpStatus getHttpCode();
}
