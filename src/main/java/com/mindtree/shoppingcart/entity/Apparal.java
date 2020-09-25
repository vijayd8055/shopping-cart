package com.mindtree.shoppingcart.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "APPARAL")
public class Apparal extends Product implements Serializable,Comparable<Apparal>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(name = "TYPE")
	private String type;

	@Column(name = "BRAND")
	private String brand;

	@Column(name = "DESIGN")
	private String design;
	
	
	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getBrand() {
		return brand;
	}


	public void setBrand(String brand) {
		this.brand = brand;
	}


	public String getDesign() {
		return design;
	}


	public void setDesign(String design) {
		this.design = design;
	}


	@Override
	public int compareTo(Apparal o) {
		// TODO Auto-generated method stub
		return 0;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((design == null) ? 0 : design.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Apparal other = (Apparal) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (design == null) {
			if (other.design != null)
				return false;
		} else if (!design.equals(other.design))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Apparal [type=" + type + ", brand=" + brand + ", design=" + design + "]";
	}


	public Apparal(int productId, String productName, int quantity, float price, List<CartProduct> cartProducts,
			String type, String brand, String design) {
		super(productId, productName, quantity, price, cartProducts);
		this.type = type;
		this.brand = brand;
		this.design = design;
	}


	public Apparal() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Apparal(int productId, String productName, int quantity, float price, List<CartProduct> cartProducts) {
		super(productId, productName, quantity, price, cartProducts);
		// TODO Auto-generated constructor stub
	}

	
}
