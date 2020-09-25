package com.mindtree.shoppingcart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mindtree.shoppingcart.service.ShoppingCartServiceImpl;

@RestController
@CrossOrigin("*")
@RequestMapping("/shopping-cart")
public class ShoppingCartController {

	//@Autowired
	//ShoppingCartServiceImpl shoppingCartService;

	/*
	 * // Search Product By ID
	 * 
	 * @RequestMapping(value = "/searchProductById/{id}", method =
	 * RequestMethod.GET) public ResponseEntity<?>
	 * searchProductById(@PathVariable("id") int id) { try { List<Object> response =
	 * null; response = shoppingCartService.searchProductById(id); return new
	 * ResponseEntity<>(response, HttpStatus.OK); } catch
	 * (ShoppingCartServiceException e) { return new ResponseEntity<>("Exception" +
	 * e.getLocalizedMessage(), HttpStatus.BAD_REQUEST); } }
	 * 
	 * // Search Product By Name
	 * 
	 * @RequestMapping(value = "/searchProductByName/{name}", method =
	 * RequestMethod.GET) public ResponseEntity<?>
	 * searchProductByName(@PathVariable("name") String name) { try { List<Object>
	 * response = null; response = shoppingCartService.searchProductByName(name);
	 * return new ResponseEntity<>(response, HttpStatus.OK); } catch
	 * (ShoppingCartServiceException e) { return new ResponseEntity<>("Exception" +
	 * e.getLocalizedMessage(), HttpStatus.BAD_REQUEST); } }
	 * 
	 * // Search Product By Category
	 * 
	 * @RequestMapping(value = "/searchProductByCategory/{category}", method =
	 * RequestMethod.GET) public ResponseEntity<?>
	 * searchProductByCategory(@PathVariable("category") String category) { try {
	 * List<Object> response = null; response =
	 * shoppingCartService.searchProductByCategory(category); return new
	 * ResponseEntity<>(response, HttpStatus.OK); } catch
	 * (ShoppingCartServiceException e) { return new ResponseEntity<>("Exception" +
	 * e.getLocalizedMessage(), HttpStatus.BAD_REQUEST); } }
	 */
}
