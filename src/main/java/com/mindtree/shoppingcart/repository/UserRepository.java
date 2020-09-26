package com.mindtree.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.mindtree.shoppingcart.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{

	@Query(value = "SELECT * FROM user_detail ud WHERE ud.user_name = :userName", nativeQuery = true)
    User findUserByUserName(@Param("userName") String userName);
	
	@Query(value = "SELECT * FROM user_detail ud WHERE ud.user_email = :email", nativeQuery = true)
    User findUserByEmail(@Param("email") String email);
}
