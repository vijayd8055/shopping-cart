package com.mindtree.shoppingcart.helper;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import com.mindtree.shoppingcart.entity.Cart;
import com.mindtree.shoppingcart.entity.Product;

@Embeddable
public class CartProductPK implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@ManyToOne
	private Cart cart;
	
	@ManyToOne
	private Product product;

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cart == null) ? 0 : cart.hashCode());
		result = prime * result + ((product == null) ? 0 : product.hashCode());
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
		CartProductPK other = (CartProductPK) obj;
		if (cart == null) {
			if (other.cart != null)
				return false;
		} else if (!cart.equals(other.cart))
			return false;
		if (product == null) {
			if (other.product != null)
				return false;
		} else if (!product.equals(other.product))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CartProductPK [cart=" + cart + ", product=" + product + "]";
	}

	public CartProductPK(Cart cart, Product product) {
		super();
		this.cart = cart;
		this.product = product;
	}

	public CartProductPK() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
