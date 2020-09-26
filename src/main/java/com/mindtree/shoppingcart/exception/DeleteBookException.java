package com.mindtree.shoppingcart.exception;

public class DeleteBookException extends ShoppingCartException{
	private static final long serialVersionUID = 1L;

	public DeleteBookException() {
		super();
	}

	public DeleteBookException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public DeleteBookException(String message, Throwable cause) {
		super(message, cause);
	}

	public DeleteBookException(String message) {
		super(message);
	}

	public DeleteBookException(Throwable cause) {
		super(cause);
	}
	
}
