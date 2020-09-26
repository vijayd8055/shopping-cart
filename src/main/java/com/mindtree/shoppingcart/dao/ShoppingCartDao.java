package com.mindtree.shoppingcart.dao;

import java.util.List;
import java.util.Optional;

import com.mindtree.shoppingcart.entity.Apparal;
import com.mindtree.shoppingcart.entity.Book;
import com.mindtree.shoppingcart.entity.Cart;
import com.mindtree.shoppingcart.entity.CartProduct;
import com.mindtree.shoppingcart.entity.Product;
import com.mindtree.shoppingcart.entity.User;
import com.mindtree.shoppingcart.exception.AddProductException;
import com.mindtree.shoppingcart.exception.DeleteApparalException;
import com.mindtree.shoppingcart.exception.DeleteBookException;
import com.mindtree.shoppingcart.exception.ProductException;
import com.mindtree.shoppingcart.exception.ShoppingCartException;

public interface ShoppingCartDao {

public String addBook(Book book) throws AddProductException;
	
	public String addApparal(Apparal apparal) throws AddProductException;
	
	
	public String addProductToCart(Cart cart) throws ShoppingCartException;
	
	public Product getProductbyId(int productId) throws ProductException;
	
	public CartProduct checkProductAlreadyPresent(int cartId, int productId) throws ProductException;
	
	public Cart getCartDetailbyId(int cartId) throws ShoppingCartException;
	
	public boolean updateProductQuantityInventory(Product product) throws ShoppingCartException;

	public String deleteBook(int id) throws DeleteBookException;
	
	public String deleteApparal(int id) throws DeleteApparalException;
	
	public CartProduct getCartProductByIds(int cartId, int productId) throws ShoppingCartException;
	
	public boolean deleteCartProductEntry(CartProduct cartProduct) throws ShoppingCartException;
	
	public boolean updateCartProduct(CartProduct cartProductPresent) throws ShoppingCartException;
	
	public boolean updateCart(Cart cartDetail) throws ShoppingCartException;
	
	public List<CartProduct> getAllCartProductDetailsByCartId(int cartId) throws ShoppingCartException;
	
	public boolean removeAllCartProductDetailsByCartId(List<CartProduct> cartProducts) throws ShoppingCartException;
	
	public List<Apparal> getAllApparalDetails() throws ShoppingCartException;
	
	public List<Book> getAllBookDetails() throws ShoppingCartException;
	
	public Apparal getApparalById(int productId) throws ShoppingCartException;
	
	public Book getBookById(int productId) throws ShoppingCartException;
	
	public List<Book> getAllBookDetailsByName(String name) throws ShoppingCartException;
	
	public List<Apparal> getAllApparalDetailsByName(String name) throws ShoppingCartException;
	
	public List<Product> getProductbyCategory(String category) throws ShoppingCartException;

	public User getUserbyID(int userId) throws ProductException;


}
