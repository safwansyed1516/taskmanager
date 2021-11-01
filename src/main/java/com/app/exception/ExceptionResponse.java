package com.app.exception;

/**
 * Class represents Exception Response.
 *
 * @author safwan
 *
 */
public class ExceptionResponse {

	private int errorCode;
	private String errorMessage;

	/**
	 * Error code getter.
	 *
	 * @return {@link int}
	 */
	public int getErrorCode() {
		return errorCode;
	}

	/**
	 * Error code setter.
	 *
	 * @param pErrorCode {@link String}
	 */
	public void setErrorCode(final int pErrorCode) {
		errorCode = pErrorCode;
	}

	/**
	 * ErrorMessage getter.
	 *
	 * @return {@link String}
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * Error message getter.
	 *
	 * @param pErrorMessage {@link String}
	 */
	public void setErrorMessage(final String pErrorMessage) {
		errorMessage = pErrorMessage;
	}

}
