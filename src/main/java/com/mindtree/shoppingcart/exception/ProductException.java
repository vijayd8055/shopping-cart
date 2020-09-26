package com.mindtree.shoppingcart.exception;

public class ProductException extends ShoppingCartException{

	private static final long serialVersionUID = 1L;

	public ProductException() {
		super();
	}

	public ProductException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ProductException(String message, Throwable cause) {
		super(message, cause);
	}

	public ProductException(String message) {
		super(message);
	}

	public ProductException(Throwable cause) {
		super(cause);
	}
}
