package com.mindtree.shoppingcart.exception;

public class AddProductException extends ShoppingCartException {

	private static final long serialVersionUID = 1L;

	public AddProductException() {
		super();
	}

	public AddProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public AddProductException(String message, Throwable cause) {
		super(message, cause);
	}

	public AddProductException(String message) {
		super(message);
	}

	public AddProductException(Throwable cause) {
		super(cause);
	}
}