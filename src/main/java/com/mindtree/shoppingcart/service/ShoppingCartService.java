package com.mindtree.shoppingcart.service;

import java.util.List;
import java.util.Map;

import com.mindtree.shoppingcart.dto.ApparalRequestDto;
import com.mindtree.shoppingcart.dto.BookRequestDto;
import com.mindtree.shoppingcart.dto.RemoveProductFromCartDto;
import com.mindtree.shoppingcart.dto.UpdateProductDto;
import com.mindtree.shoppingcart.dto.UserProductDto;
import com.mindtree.shoppingcart.entity.User;
import com.mindtree.shoppingcart.exception.ShoppingCartServiceException;

public interface ShoppingCartService {
public String addBook(BookRequestDto newBook) throws ShoppingCartServiceException;
	
	public String addApparal(ApparalRequestDto newApparal) throws ShoppingCartServiceException;
	
	public String addToCart(UserProductDto userProduct) throws ShoppingCartServiceException;
	
	public String deleteProductById(int id) throws ShoppingCartServiceException ;
	
	public String removeProductFromCart(RemoveProductFromCartDto removeProductById)
			throws ShoppingCartServiceException ;
	
	public String removeAllProductFromCart(User user) throws ShoppingCartServiceException ;
	
	public List<Object> viewAllProduct() throws ShoppingCartServiceException;
	
	public Map<String, Object> viewMyCart(User user) throws ShoppingCartServiceException;
	
	public List<Object> searchProductById(int id) throws ShoppingCartServiceException;
	
	public List<Object> searchProductByName(String name) throws ShoppingCartServiceException;
	
	public List<Object> searchProductByCategory(String category) throws ShoppingCartServiceException;
	
	public String updateProductInCart(UpdateProductDto updateProduct) throws ShoppingCartServiceException;

}
