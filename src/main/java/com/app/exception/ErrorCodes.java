package com.app.exception;

import java.text.MessageFormat;

import org.springframework.http.HttpStatus;

public enum ErrorCodes implements IErrorCode {
	// Order specific response error codes and error response.
	UNAUTHORIZED(1001, HttpStatus.UNAUTHORIZED, "Incorrect Username/Password."),
	FAILED_TO_GET_USER(1002, HttpStatus.BAD_REQUEST, "Failed to get user."),
	FAILED_TO_SAVE_USER(1003, HttpStatus.BAD_REQUEST, "Failed to save user."),
	FAILED_TO_DELETE_USER(1004, HttpStatus.NOT_FOUND, "User not found."),
	USER_DELETED(1005, HttpStatus.OK, "User deleted sucessfully."),
	USER_SAVED(1006, HttpStatus.OK, "User saved sucessfully."),
	INVALID_TOKEN(1007, HttpStatus.UNAUTHORIZED, "Invalid token."),
	TOKEN_EXPIRED(1008, HttpStatus.BAD_REQUEST, "Token expired"),;

	/** Begin of the error codes range. */
	public static final int ERROR_CODE_RANGE_BASE = 1000;
	/** End of the error codes range. */
	public static final int ERROR_CODE_RANGE_MAX = 1999;

	/** Error code. */
	private final Integer code;

	/** Error text message. */
	private final String message;

	private final HttpStatus httpCode;

	ErrorCodes(final Integer pCode, final HttpStatus pHttpCode, final String pMessage) {
		code = pCode + ERROR_CODE_RANGE_BASE;
		message = pMessage;
		httpCode = pHttpCode;
	}

	@Override
	public final Integer getCode() {
		return code;
	}

	public final String getMessage() {
		return message;
	}

	@Override
	public final HttpStatus getHttpCode() {
		return httpCode;
	}

	/**
	 * getter for message.
	 *
	 * @param pArguments the arguments
	 * @return the formatted message
	 */

	public final String getFormattedMessage(final Object... pArguments) {
		return MessageFormat.format(this.getMessage(), pArguments);
	}

	/**
	 *
	 * @return the Service exception
	 */

	public final TaskManagerServiceException createTaskManagerServiceException(final Object... pArguments) {
		return new TaskManagerServiceException(this, this.getFormattedMessage(pArguments));
	}

	/**
	 *
	 * @param pMessageArguments the message arguments
	 * @return the Service exception with arguments
	 */
	public final TaskManagerServiceException createTaskManagerServiceException(final Throwable pCause,
			final Object... pArguments) {
		return new TaskManagerServiceException(this, this.getFormattedMessage(pArguments), pCause);
	}

}