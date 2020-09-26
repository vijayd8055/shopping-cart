package com.mindtree.shoppingcart.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mindtree.shoppingcart.dao.ShoppingCartDao;
import com.mindtree.shoppingcart.dto.ApparalResponseDto;
import com.mindtree.shoppingcart.dto.BookResponseDto;
import com.mindtree.shoppingcart.dto.RemoveProductFromCartDto;
import com.mindtree.shoppingcart.dto.UpdateProductDto;
import com.mindtree.shoppingcart.dto.UserProductDto;
import com.mindtree.shoppingcart.entity.Apparal;
import com.mindtree.shoppingcart.entity.Book;
import com.mindtree.shoppingcart.entity.Cart;
import com.mindtree.shoppingcart.entity.CartProduct;
import com.mindtree.shoppingcart.entity.Product;
import com.mindtree.shoppingcart.entity.User;
import com.mindtree.shoppingcart.exception.DeleteApparalException;
import com.mindtree.shoppingcart.exception.DeleteBookException;
import com.mindtree.shoppingcart.exception.ProductException;
import com.mindtree.shoppingcart.exception.ShoppingCartException;
import com.mindtree.shoppingcart.exception.ShoppingCartServiceException;
import com.mindtree.shoppingcart.helper.CartProductPK;
import com.mindtree.shoppingcart.helper.ProductResponseObject;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {

	@Autowired
	ShoppingCartDao dao;

	@Autowired
	ProductResponseObject productResponseObject;

	Logger logger = LoggerFactory.getLogger(ShoppingCartServiceImpl.class);

	private Properties prop;

	public ShoppingCartServiceImpl() {
		prop = new Properties();
		try (InputStream input = new FileInputStream("src/main/resources/constants.properties")) {
			prop.load(input);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	// --- Search ---

	// Search Product by Product ID
	public List<Object> searchProductById(int id) throws ShoppingCartServiceException {
		String message = null;
		try {
			List<Object> products = new ArrayList<Object>();

			Product product = dao.getProductbyId(id);

			if (null == product) {
				message = "No Product present with id:" + id;
				products.add(message);
				logger.info(message);
				return products;
			}

			if (product.getProductName().equals(prop.getProperty("cart.apparaltype"))) {
				Apparal apparal = dao.getApparalById(product.getProductId());
				if (apparal == null) {
					message = "No Apparal Product present with id:" + product.getProductId();
					products.add(message);
					logger.info(message);
					return products;
				}
				ApparalResponseDto apparalDto = productResponseObject.createApparalDtoResponseObject(apparal, 0);
				products.add(apparalDto);

			} else if (product.getProductName().equals(prop.getProperty("cart.booktype"))) {
				Book book = dao.getBookById(product.getProductId());
				if (book == null) {
					message = "No Book Product present with id:" + product.getProductId();
					products.add(message);
					logger.info(message);
					return products;
				}
				BookResponseDto bookDto = productResponseObject.createBookDtoResponseObject(book, 0);
				products.add(bookDto);
			}

			return products;
		} catch (ShoppingCartException e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// Add Product To Cart
	public String addToCart(UserProductDto userProduct) throws ShoppingCartServiceException {
		String message = null;
		try {
			int productIdCount = 1;
			// int quantity = userProduct.getQuantity();
			double cartValue = 0;
			Cart cart = new Cart();

			List<CartProduct> cartProductList = new ArrayList<CartProduct>();
			CartProduct cartProduct = new CartProduct();
			CartProductPK cartProductPK = new CartProductPK();

			// Get User
			User user = dao.getUserbyID(userProduct.getUserId());
			if (null == user) {
				message = "User not present with id:" + userProduct.getUserId();
				logger.info(message);
				return message;
			}

			Cart cartDetail = dao.getCartDetailbyId(user.getCart().getCartId());
			cartValue = cartDetail.getTotalAmount();

			// Get Product by product Id
			Product product = dao.getProductbyId(userProduct.getProductId());

			if (null == product) {
				message = "No Product present with id:" + userProduct.getProductId();
				logger.info(message);
				return message;
			}

			cart.setCartId(user.getCart().getCartId());

			CartProduct resultCartProduct = increaseProductInCart(user.getCart().getCartId(),
					userProduct.getProductId(), userProduct.getQuantity());
			if (null != resultCartProduct) { // increases the quantity of product
				cartProduct.setQuantity(resultCartProduct.getQuantity() + userProduct.getQuantity());
			} else {
				cartProduct.setQuantity(productIdCount);
			}

			cartValue = cartValue + product.getPrice(); // total cart value
			cart.setTotalAmount(cartValue);
			cartProductPK.setCart(cart);
			cartProductPK.setProduct(product);
			cartProduct.setPk(cartProductPK);

			cartProductList.add(cartProduct);
			cart.setCartProducts(cartProductList);

			if ((product.getQuantity()) <= 0) {
				message = "No more Product with id:" + userProduct.getProductId();
				logger.info(message);
				return message;
			} else {
				product.setQuantity(product.getQuantity() - 1);
				dao.updateProductQuantityInventory(product);
			}

			return dao.addProductToCart(cart);
		} catch (ShoppingCartException e) {
			throw new ShoppingCartServiceException(e);
		}

	}

	// # METHOD
	public CartProduct increaseProductInCart(int cartId, int productId, int quantity)
			throws ShoppingCartServiceException {
		try {
			CartProduct cartProduct = dao.checkProductAlreadyPresent(cartId, productId, quantity);
			return (null != cartProduct) ? cartProduct : null;
		} catch (ProductException e) {
			throw new ShoppingCartServiceException(e);
		}

	}

	// Delete Product by ID
	public String deleteProductById(int id) throws ShoppingCartServiceException {
		try {
			String message = null;
			Product product = dao.getProductbyId(id);
			if (null == product) {
				message = "No Product present with id:" + id;
				logger.info(message);
				return message;
			}

			return ("BOOK".equals(product.getProductName())) ? dao.deleteBook(id) : dao.deleteApparal(id);
		} catch (DeleteApparalException e) {
			throw new ShoppingCartServiceException(e);
		} catch (DeleteBookException e) {
			throw new ShoppingCartServiceException(e);
		} catch (ProductException e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// Remove Product From Cart
	public String removeProductFromCart(RemoveProductFromCartDto removeProductById)
			throws ShoppingCartServiceException {
		String message = null;
		try {
			boolean resultCartProductEntry = false;
			int quantity = 1;
			double cartValue = 0;

			if (quantity < 0) {
				message = "Quantity Cannot be Negative";
				logger.info(message);
				throw new NumberFormatException(message);
			}
			// Get User
			User user = dao.getUserbyID(removeProductById.getUserId());
			if (null == user) {
				message = "User not present with ID:" + removeProductById.getUserId();
				return message;
			}
			// Get Cart Detail
			Cart cartDetail = dao.getCartDetailbyId(user.getCart().getCartId());
			cartValue = cartDetail.getTotalAmount(); // cart value

			// Get Product by product Id
			Product product = dao.getProductbyId(removeProductById.getProductId());

			CartProduct cartProductPresent = dao.getCartProductByIds(cartDetail.getCartId(),
					removeProductById.getProductId());

			if (null == cartProductPresent) {
				message = "No Cart present with Product id:" + removeProductById.getProductId();
				logger.info(message);
				return message;
			}

			if (1 == cartProductPresent.getQuantity()) { // delete CartProduct entry.
				resultCartProductEntry = dao.deleteCartProductEntry(cartProductPresent);
			} else {
				cartProductPresent.setQuantity(cartProductPresent.getQuantity() - 1);
				resultCartProductEntry = dao.updateCartProduct(cartProductPresent);
			}

			if (true == resultCartProductEntry) {
				cartValue = cartValue - product.getPrice(); // total cart value
				cartDetail.setTotalAmount(cartValue);
				resultCartProductEntry = dao.updateCart(cartDetail);
				product.setQuantity(product.getQuantity() + 1);
				resultCartProductEntry = dao.updateProductQuantityInventory(product);
			}

			return (true == resultCartProductEntry) ? "Product removed from Cart."
					: "Error in Removing Product from Cart.";
		} catch (NumberFormatException e) {
			throw new ShoppingCartServiceException(e);
		} catch (ShoppingCartException e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// Remove All Product from Cart
	public String removeAllProductFromCart(User user) throws ShoppingCartServiceException {
		String message = null;
		try {
			boolean resultCartProductEntry = false;
			double cartValue = 0;
			double totalPriceReduced = 0;
			List<CartProduct> cartProducts = null;

			// Get User
			User userResponse = dao.getUserbyID(user.getUserId());

			if (null == userResponse) {
				message = "User not present with id:" + user.getUserId();
				logger.info(message);
				return message;
			}
			// Get Cart Detail
			Cart cartDetail = dao.getCartDetailbyId(user.getCart().getCartId());
			cartValue = cartDetail.getTotalAmount(); // cart value

			cartProducts = dao.getAllCartProductDetailsByCartId(cartDetail.getCartId());

			if (cartProducts.isEmpty()) {
				message = "No Product present!!!";
				logger.info(message);
				return message;
			}

			for (CartProduct cartProduct : cartProducts) {
				totalPriceReduced = totalPriceReduced + updateCartAndProductQuantityInventory(cartProduct);
			}
			cartValue = cartValue - totalPriceReduced;
			cartDetail.setTotalAmount(cartValue);
			resultCartProductEntry = dao.updateCart(cartDetail);

			if (resultCartProductEntry == true) {
				resultCartProductEntry = dao.removeAllCartProductDetailsByCartId(cartProducts);
			}

			return (true == resultCartProductEntry) ? "All Product removed from Cart."
					: "Error in Removing Products from Cart.";
		} catch (Exception e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// #Method Update Inventory Quantity
	private double updateCartAndProductQuantityInventory(CartProduct cartProduct) throws ShoppingCartServiceException {
		try {
			int quantityInCart = cartProduct.getQuantity();
			double totalPriceReduced = 0;
			Product product = dao.getProductbyId(cartProduct.getPk().getProduct().getProductId());
			if (null != product) {
				product.setQuantity(product.getQuantity() + quantityInCart);
				dao.updateProductQuantityInventory(product);
			}
			totalPriceReduced = product.getPrice() * quantityInCart;
			return totalPriceReduced;
		} catch (Exception e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// View All Products
	public List<Object> viewAllProduct() throws ShoppingCartServiceException {
		try {
			List<Apparal> apparalList = dao.getAllApparalDetails();
			List<Book> bookList = dao.getAllBookDetails();
			List<Object> prods = new ArrayList<Object>();
			Collections.sort(apparalList);
			Collections.sort(bookList);

			for (Apparal apparal : apparalList) {
				ApparalResponseDto apparalDto = productResponseObject.createApparalDtoResponseObject(apparal, 0);
				prods.add(apparalDto);
			}

			for (Book book : bookList) {
				BookResponseDto bookDto = productResponseObject.createBookDtoResponseObject(book, 0);
				prods.add(bookDto);
			}

			return prods;
		} catch (Exception e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// View My Cart
	public Map<String, Object> viewMyCart(User user) throws ShoppingCartServiceException {
		String message = null;
		try {
			double totalCartValue = 0;
			List<CartProduct> cartProducts = new ArrayList<>();
			List<Object> productList = new ArrayList<Object>();
			Map<String, Object> productMap = new HashMap<String, Object>();

			// Get Cart Detail
			Cart cartDetail = dao.getCartDetailbyId(user.getCart().getCartId());
			cartProducts = dao.getAllCartProductDetailsByCartId(cartDetail.getCartId());

			if (cartProducts.isEmpty()) {
				message = "No Product present in Cart id:" + cartDetail.getCartId();
				productMap.put("message", message);
				logger.info(message);
				return productMap;
			}

			for (CartProduct cartProduct : cartProducts) {
				if (dao.checkProductType(cartProduct.getPk().getProduct().getProductId())
						.equals(prop.getProperty("cart.apparaltype"))) {
					Apparal apparal = dao.getApparalById(cartProduct.getPk().getProduct().getProductId());
					ApparalResponseDto apparalDto = productResponseObject.createApparalDtoResponseObject(apparal,
							cartProduct.getQuantity());
					totalCartValue = totalCartValue + getCalculateTotalPrice(cartProduct);
					productList.add(apparalDto);

				} else if (dao.checkProductType(cartProduct.getPk().getProduct().getProductId())
						.equals(prop.getProperty("cart.booktype"))) {
					Book book = dao.getBookById(cartProduct.getPk().getProduct().getProductId());

					if (book == null)
						continue;
					BookResponseDto bookDto = productResponseObject.createBookDtoResponseObject(book,
							cartProduct.getQuantity());
					totalCartValue = totalCartValue + getCalculateTotalPrice(cartProduct);
					productList.add(bookDto);
				}
			}
			productMap.put("ProductList", productList);
			productMap.put("TotalCartValue", totalCartValue);
			return productMap;
		} catch (ShoppingCartException e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// Search Product by Product Name
	@SuppressWarnings({ "null", "unused" })
	public List<Object> searchProductByName(String name) throws ShoppingCartServiceException {
		String message = null;
		try {
			List<Object> prods = new ArrayList<Object>();
			List<Apparal> apparalList = new ArrayList<Apparal>();
			List<Book> bookList = new ArrayList<Book>();
			List<Integer> ids = dao.getProductIDByName(name);
			if (ids != null) {
				for (int id : ids) {
					if (dao.checkProductType(id).equals(prop.getProperty("cart.apparaltype")))
						apparalList.add(dao.getApparalById(id));
					else
						bookList.add(dao.getBookById(id));
				}
			}

			if (!apparalList.isEmpty()) {
				for (Apparal apparal : apparalList) {
					ApparalResponseDto apparalDto = productResponseObject.createApparalDtoResponseObject(apparal, 0);
					prods.add(apparalDto);
				}
			}

			if (!bookList.isEmpty()) {
				for (Book book : bookList) {
					BookResponseDto bookDto = productResponseObject.createBookDtoResponseObject(book, 0);
					prods.add(bookDto);
				}
			}
			if (apparalList == null && bookList == null) {
				message = "No Product present with name:" + name;
				prods.add(message);
				logger.info(message);
				return prods;
			}
			return prods;
		} catch (ShoppingCartException e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// Search Product by Product Category
	public List<Object> searchProductByCategory(String category) throws ShoppingCartServiceException {
		String message = null;
		try {
			List<Object> prods = new ArrayList<Object>();
			if (category.equals(prop.getProperty("cart.booktype"))) {
				List<Book> books = dao.getAllBookDetails();
				Collections.sort(books);
				for (Book book : books) {
					BookResponseDto bookResponseDto = productResponseObject.createBookDtoResponseObject(book, 0);
					prods.add(bookResponseDto);
				}
				return prods;
			} else if (category.equals(prop.getProperty("cart.apparaltype"))) {
				List<Apparal> apparals = dao.getAllApparalDetails();
				Collections.sort(apparals);
				for (Apparal apparal : apparals) {
					ApparalResponseDto apparalResponseDto = productResponseObject.createApparalResponseObject(apparal,
							0);
					prods.add(apparalResponseDto);
				}
				return prods;
			} else {
				message = "Entered Category is not present.";
				prods.add(message);
				return prods;
			}

		} catch (ShoppingCartException e) {
			throw new ShoppingCartServiceException(e);
		}
	}

	// # Method
	private double getCalculateTotalPrice(CartProduct product) {
		return (product.getQuantity() * product.getPk().getProduct().getPrice());
	}

	// Update Product In Cart
	public String updateProductInCart(UpdateProductDto updateProduct) throws ShoppingCartServiceException {
		String message = null;
		try {
			boolean resultCartProductEntry = false;
			int updateQuantity = updateProduct.getQuantity();
			double cartValue = 0;

			if (updateQuantity < 0) {
				message = "Quantity Cannot be Negative";
				logger.info(message);
				throw new NumberFormatException(message);
			}
			// Get User
			User userDetail = dao.getUserbyID(updateProduct.getUserId());
			if (null == userDetail) {
				message = "User not present with ID:" + updateProduct.getUserId();
				logger.info(message);
				return message;
			}
			// Get Cart Detail
			Cart cartDetail = dao.getCartDetailbyId(userDetail.getCart().getCartId());
			cartValue = cartDetail.getTotalAmount(); // cart value

			// Get Product by product Id
			Product product = dao.getProductbyId(updateProduct.getProductId());

			CartProduct cartProductPresent = dao.getCartProductByIds(cartDetail.getCartId(),
					updateProduct.getProductId());

			if (null == cartProductPresent) {
				message = "No Cart present with Product id:" + updateProduct.getProductId();
				logger.info(message);
				return message;
			}

			if (0 == updateQuantity) { // delete CartProduct entry.

				int quantityGap = cartProductPresent.getQuantity();

				resultCartProductEntry = dao.deleteCartProductEntry(cartProductPresent);
				if (true == resultCartProductEntry) {
					cartValue = cartValue - product.getPrice() * quantityGap; // total cart value
					cartDetail.setTotalAmount(cartValue);
					resultCartProductEntry = dao.updateCart(cartDetail);
					product.setQuantity(product.getQuantity() + quantityGap); // Increase Product Inventory
					resultCartProductEntry = dao.updateProductQuantityInventory(product);
				}
			} else if (updateQuantity == cartProductPresent.getQuantity()) {
				message = "Cannot Update!! No Change in request!!";
				logger.info(message);
				return message;
			} else if (updateQuantity > cartProductPresent.getQuantity()) {
				int quantityGap = updateQuantity - cartProductPresent.getQuantity();
				int quantityAtPresent = product.getQuantity();

				if (quantityGap > quantityAtPresent) {
					message = "Product Quantity exceeds Max. Inventory Storage";
					logger.info(message);
					return message;
				} else {
					cartProductPresent.setQuantity(cartProductPresent.getQuantity() + quantityGap);
					resultCartProductEntry = dao.updateCartProduct(cartProductPresent);

					if (true == resultCartProductEntry) {
						cartValue = cartValue + product.getPrice() * quantityGap; // total cart value
						cartDetail.setTotalAmount(cartValue);
						resultCartProductEntry = dao.updateCart(cartDetail);
						product.setQuantity(product.getQuantity() - quantityGap); // decrease Product Inventory
						resultCartProductEntry = dao.updateProductQuantityInventory(product);
					}
				}
			} else if (updateQuantity < cartProductPresent.getQuantity()) {
				int quantityGap = cartProductPresent.getQuantity() - updateQuantity;

				cartProductPresent.setQuantity(cartProductPresent.getQuantity() - quantityGap);
				resultCartProductEntry = dao.updateCartProduct(cartProductPresent);

				if (true == resultCartProductEntry) {
					cartValue = cartValue - product.getPrice() * quantityGap; // total cart value
					cartDetail.setTotalAmount(cartValue);
					resultCartProductEntry = dao.updateCart(cartDetail);
					product.setQuantity(product.getQuantity() + quantityGap); // Increase Product Inventory
					resultCartProductEntry = dao.updateProductQuantityInventory(product);
				}
			}

			return (true == resultCartProductEntry) ? "Product Updated in Cart." : "Error in Updating Product in Cart.";
		} catch (NumberFormatException e) {
			throw new ShoppingCartServiceException(e);
		} catch (Exception e) {
			throw new ShoppingCartServiceException(e);
		}
	}
}
