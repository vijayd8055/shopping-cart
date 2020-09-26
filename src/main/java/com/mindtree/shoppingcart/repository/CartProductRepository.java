package com.mindtree.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindtree.shoppingcart.entity.CartProduct;

@Repository
public interface CartProductRepository extends JpaRepository<CartProduct, Integer>{


	@Query(value = "SELECT * FROM cart_product cp where cp.cart_cart_id=:cartId and cp.product_product_id=:productId", nativeQuery = true)
    CartProduct findCartProduct(@Param("cartId") int cartId ,@Param("productId") int productId);

	@Query(value = "SELECT * FROM cart_product cp where cp.cart_cart_id=:cartId", nativeQuery = true)
	List<CartProduct> findAllById(@Param("cartId") int cartId);

	@Query(value = "DELETE FROM cart_product cp where cp.cart_cart_id=:cartId", nativeQuery = true)
	void deleteCartProductEntitiesByCartId(@Param("cartId") int cartId);
}
