package com.mindtree.shoppingcart.dto;

import java.io.Serializable;

public class UpdateProductDto implements Serializable{
	private static final long serialVersionUID = 1L;
	private int userId;
	private int productId;
	private int quantity;

	

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + productId;
		result = prime * result + quantity;
		result = prime * result + userId;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UpdateProductDto other = (UpdateProductDto) obj;
		if (productId != other.productId)
			return false;
		if (quantity != other.quantity)
			return false;
		if (userId != other.userId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UpdateProductDto [userId=" + userId + ", productId=" + productId + ", quantity=" + quantity + "]";
	}
}
