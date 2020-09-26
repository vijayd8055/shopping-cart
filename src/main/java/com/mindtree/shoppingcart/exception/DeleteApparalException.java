package com.mindtree.shoppingcart.exception;

public class DeleteApparalException extends ShoppingCartException{
	private static final long serialVersionUID = 1L;

	public DeleteApparalException() {
		super();
	}

	public DeleteApparalException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeleteApparalException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeleteApparalException(String message) {
		super(message);
	}

	public DeleteApparalException(Throwable cause) {
		super(cause);
	}

}
