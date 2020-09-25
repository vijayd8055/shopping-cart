package com.mindtree.shoppingcart.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="CART")
public class Cart implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "CART_ID")
	private int cartId;
	
	@Column(name = "TOTAL_AMOUNT")
	private double totalAmount;
	
	@OneToMany(mappedBy = "pk.cart", cascade=CascadeType.ALL , fetch = FetchType.LAZY)
	private List<CartProduct> cartProducts;

	public int getCartId() {
		return cartId;
	}

	public void setCartId(int cartId) {
		this.cartId = cartId;
	}

	public double getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(double totalAmount) {
		this.totalAmount = totalAmount;
	}

	public List<CartProduct> getCartProducts() {
		return cartProducts;
	}

	public void setCartProducts(List<CartProduct> cartProducts) {
		this.cartProducts = cartProducts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + cartId;
		result = prime * result + ((cartProducts == null) ? 0 : cartProducts.hashCode());
		long temp;
		temp = Double.doubleToLongBits(totalAmount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		Cart other = (Cart) obj;
		if (cartId != other.cartId)
			return false;
		if (cartProducts == null) {
			if (other.cartProducts != null)
				return false;
		} else if (!cartProducts.equals(other.cartProducts))
			return false;
		if (Double.doubleToLongBits(totalAmount) != Double.doubleToLongBits(other.totalAmount))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cart [cartId=" + cartId + ", totalAmount=" + totalAmount + ", cartProducts=" + cartProducts + "]";
	}

	public Cart(int cartId, double totalAmount, List<CartProduct> cartProducts) {
		super();
		this.cartId = cartId;
		this.totalAmount = totalAmount;
		this.cartProducts = cartProducts;
	}

	public Cart() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
