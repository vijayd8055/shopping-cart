package com.mindtree.shoppingcart.exception;

public class ShoppingCartServiceException extends ShoppingCartException {

	private static final long serialVersionUID = 1L;

	public ShoppingCartServiceException() {
		super();
	}

	public ShoppingCartServiceException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ShoppingCartServiceException(String message, Throwable cause) {
		super(message, cause);
	}

	public ShoppingCartServiceException(String message) {
		super(message);
	}

	public ShoppingCartServiceException(Throwable cause) {
		super(cause);
	}

}
