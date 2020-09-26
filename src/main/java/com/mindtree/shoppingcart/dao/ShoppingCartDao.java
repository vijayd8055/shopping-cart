package com.mindtree.shoppingcart.dao;

import com.mindtree.shoppingcart.entity.Apparal;
import com.mindtree.shoppingcart.entity.Product;
import com.mindtree.shoppingcart.exception.ProductException;

public interface ShoppingCartDao {

	Product getProductbyId(int id) throws ProductException;

	Apparal getApparalById(int productId);

}
