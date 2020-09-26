package com.mindtree.shoppingcart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindtree.shoppingcart.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

	@Query(value = "SELECT p.product_id FROM Product p where p.product_name=:productName", nativeQuery = true)
    List<Integer> getProductIDByName(@Param("productName") String productName);
}
