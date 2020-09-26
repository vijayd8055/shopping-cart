package com.mindtree.shoppingcart.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
import com.mindtree.shoppingcart.repository.ApparalRepository;
import com.mindtree.shoppingcart.repository.BookRepository;
import com.mindtree.shoppingcart.repository.CartProductRepository;
import com.mindtree.shoppingcart.repository.CartRepository;
import com.mindtree.shoppingcart.repository.ProductRepository;
import com.mindtree.shoppingcart.repository.UserRepository;

@Service
@Transactional
public class ShoppingCartDaoImpl implements ShoppingCartDao {

	Logger logger = LoggerFactory.getLogger(ShoppingCartDaoImpl.class);

	@Autowired
	BookRepository bookRepo;

	@Autowired
	ApparalRepository apparalRepo;

	@Autowired
	UserRepository userRepo;

	@Autowired
	CartRepository cartRepo;

	@Autowired
	ProductRepository prodRepo;

	@Autowired
	CartProductRepository cartProductRepo;

	@PersistenceContext
	private EntityManager entityManager;

	// ---Search---

	// Get Product By Id
	public Product getProductbyId(int productId) throws ProductException {
		try {
			String message = "";
			if (prodRepo.findById(productId).isPresent())
				return prodRepo.findById(productId).get();
			else
				message = "Product ID is Invalid!";
			logger.info(message);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage() + ": Error in getProductbyId()");
			throw new ProductException(e.getMessage() + ": Error in getProductbyId()");
		}

	}

	// Add Cart to User
	public String addProductToCart(Cart cart) throws ShoppingCartException {
		try {
			String message = "";
			cartRepo.save(cart);
			message = "Product added to Cart.";
			logger.info(message);
			return message;
		} catch (Exception e) {
			logger.error(e.getMessage() + ": Error in addProductToCart()");
			throw new ShoppingCartException(e.getMessage() + ": addProductToCart()");
		}
	}

	// Check Product Already Present in the Cart by ID
	public CartProduct checkProductAlreadyPresent(int cartId, int productId, int quantity) throws ProductException {
		try {
			return cartProductRepo.findCartProduct(cartId, productId);
		} catch (Exception e) {
			logger.error(e.getMessage() + ": Error in checkProductAlreadyPresent()");
			throw new ProductException(e.getMessage() + ": Error in checkProductAlreadyPresent()");
		}
	}

	// Get Cart Detail
	public Cart getCartDetailbyId(int cartId) throws ShoppingCartException {
		try {
			return cartRepo.findById(cartId).get();
		} catch (Exception e) {
			logger.error(e.getMessage() + ": Error in getCartDetailbyId()");
			throw new ShoppingCartException(e.getMessage() + ": getCartDetailbyId()");
		}

	}

	// Update Quantity from Product Inventory
	public boolean updateProductQuantityInventory(Product product) throws ShoppingCartException {
		try {
			prodRepo.save(product);
			logger.info("Product Updated");
			return true;
		} catch (Exception e) {
			logger.error(e.getMessage() + ": Error in updateProductQuantityInventory()");
			throw new ShoppingCartException(e.getMessage() + ": updateProductQuantityInventory()");
		}
	}

	// Delete Book product by ID
	public String deleteBook(int id) throws DeleteBookException {
		String message = "";
		try {
			if (bookRepo.findById(id).isPresent()) {
				bookRepo.deleteById(id);
				message = "Book delete Successfully.";
				logger.info(message);
			} else {
				logger.info("No record exist for given id");
				throw new EntityNotFoundException("No record exist for given id");
			}
			return message;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in deleteBook()");
			throw new DeleteBookException(e.getLocalizedMessage() + ": Error in deleteBook()");
		}
	}

	// Delete Apparel product by ID
	public String deleteApparal(int id) throws DeleteApparalException {
		String message = "";
		try {
			if (apparalRepo.findById(id).isPresent()) {
				apparalRepo.deleteById(id);
				message = "Apparal deleted Successfully.";
				logger.info(message);
			} else {
				logger.info("No record exist for given id");
				throw new EntityNotFoundException("No record exist for given id");
			}
			return message;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in deleteApparal()");
			throw new DeleteApparalException(e.getLocalizedMessage() + ": Error in deleteApparal()");
		}
	}

	// Get CartProduct Entity By ID
	public CartProduct getCartProductByIds(int cartId, int productId) throws ShoppingCartException {
		CartProduct cartProduct = null;
		try {
			cartProduct = cartProductRepo.findCartProduct(cartId, productId);
			return (null != cartProduct) ? cartProduct : null;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in getCartProductByIds()");
			throw new ShoppingCartException(e.getMessage() + ":: Error in getCartProductByIds()");
		}
	}

	// Delete CartProductEntry
	public boolean deleteCartProductEntry(CartProduct cartProduct) throws ShoppingCartException {
		boolean resultDeleted = false;
		try {
			cartProductRepo.delete(cartProduct);
			resultDeleted = true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in deleteCartProductEntry()");
			throw new ShoppingCartException(e.getMessage() + ": Error in deleteCartProductEntry()");
		}
		return resultDeleted;
	}

	// Update CartProductEntry
	public boolean updateCartProduct(CartProduct cartProductPresent) throws ShoppingCartException {
		boolean updateResult = false;
		try {
			cartProductRepo.save(cartProductPresent);
			updateResult = true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in updateCartProduct()");
			throw new ShoppingCartException(e.getMessage() + ": Error in updateCartProduct()");
		}
		return updateResult;
	}

	// Update Cart
	public boolean updateCart(Cart cartDetail) throws ShoppingCartException {
		boolean updateResult = false;
		try {
			cartRepo.save(cartDetail);
			updateResult = true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in updateCart()");
			throw new ShoppingCartException(e.getMessage() + ": Error in updateCart()");
		}
		return updateResult;
	}

	// Get All CartProduct By CartId
	public List<CartProduct> getAllCartProductDetailsByCartId(int cartId) throws ShoppingCartException {
		try {
			return cartProductRepo.findAllById(cartId);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in getAllCartProductDetailsByCartId()");
			throw new ShoppingCartException(e.getMessage() + ": Error in getAllCartProductDetailsByCartId()");
		}
	}

	// remove All CartProduct Details By CartId
	public boolean removeAllCartProductDetailsByCartId(List<CartProduct> cartProducts) throws ShoppingCartException {
		boolean resultDeleted = false;
		try {
			cartProductRepo.deleteAll(cartProducts);
			// cartProductRepo.deleteCartProductEntitiesByCartId(cartProducts);
			resultDeleted = true;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in removeAllCartProductDetailsByCartId()");
			throw new ShoppingCartException(e.getMessage() + ": Error in removeAllCartProductDetailsByCartId()");
		}
		return resultDeleted;
	}

	// get All Apparel Details
	public List<Apparal> getAllApparalDetails() throws ShoppingCartException {
		try {
			return (List<Apparal>) apparalRepo.findAll();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in getAllApparalDetails()");
			throw new ShoppingCartException(e.getMessage() + ": Error in getAllApparalDetails()");
		}
	}

	// get All Book Details
	public List<Book> getAllBookDetails() throws ShoppingCartException {
		try {
			return (List<Book>) bookRepo.findAll();
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in getAllBookDetails()");
			throw new ShoppingCartException(e.getMessage() + ": Error in getAllBookDetails()");
		}
	}

	// Get Apparel by ID
	public Apparal getApparalById(int productId) throws ShoppingCartException {
		try {
			return (apparalRepo.existsById(productId) ? apparalRepo.getOne(productId) : null);
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in getApparalById()");
			throw new ShoppingCartException(e.getMessage() + ": Error in getApparalById()");
		}
	}

	// Get Book by ID
	public Book getBookById(int productId) throws ShoppingCartException {
		try {
			if (bookRepo.existsById(productId)) {
				return bookRepo.getOne(productId);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in getBookById()");
			throw new ShoppingCartException(e.getMessage() + ": Error in getBookById()");
		}
	}

	// Get Product Details By Category
	public List<Product> getProductbyCategory(String category) throws ShoppingCartException {
		try {
			CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
			CriteriaQuery<Product> createQuery = criteriaBuilder.createQuery(Product.class);
			Root<Product> from = createQuery.from(Product.class);

			Expression<String> path = from.get("productName");
			ParameterExpression<String> param = criteriaBuilder.parameter(String.class);

			createQuery.select(from).where(criteriaBuilder.like(path, param));
			TypedQuery<Product> typedQuery = entityManager.createQuery(createQuery);
			typedQuery.setParameter(param, "%" + category + "%");
			List<Product> productList = typedQuery.getResultList();

			entityManager.close();
			return productList;
		} catch (Exception e) {
			logger.error(e.getLocalizedMessage() + ": Error in getProductbyCategory()");
			throw new ShoppingCartException(e.getMessage() + ": Error in getProductbyCategory()");
		}
	}

	@Override
	public User getUserbyID(int userId) throws ProductException {
		try {
			String message = "";
			if (userRepo.findById(userId) != null)
				return userRepo.getOne(userId);
			else
				message = "Product ID is Invalid!";
			logger.info(message);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage() + ": Error in getProductbyId()");
			throw new ProductException(e.getMessage() + ": Error in getProductbyId()");
		}
	}

	@Override
	public List<Integer> getProductIDByName(String name) throws ProductException {
		try {
			String message = "";
			List<Integer> ids = prodRepo.getProductIDByName(name);
			if (ids != null)
				return ids;
			else
				message = "Product name is Invalid!";
			logger.info(message);
			return null;
		} catch (Exception e) {
			logger.error(e.getMessage() + ": Error in getProductIDByName()");
			throw new ProductException(e.getMessage() + ": Error in getProductIDByName()");
		}

	}

	@Override
	public String checkProductType(int id) throws ProductException {
		try {
			String message = "";
			if (bookRepo.existsById(id)) {
				return "BOOK";
			} else if (apparalRepo.existsById(id)) {
				return "APPARAL";
			} else {
				message = "Product ID is Invalid!";
				logger.info(message);
				return null;
			}
		} catch (Exception e) {
			logger.error(e.getMessage() + ": Error in checkProductType()");
			throw new ProductException(e.getMessage() + ": Error in checkProductType()");
		}

	}
}
