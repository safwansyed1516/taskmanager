package com.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.entity.User;

@Repository
public interface LoginRepository extends JpaRepository<User, Long> {

	@Query("select r from User r  where r.email=:email")
	User getByEmail(@Param("email") String email);

	@Query("UPDATE User r SET r.isDeleted=true where r.userId=:userId")
	int deleteUser(@Param("userId") Long userId);

	User findByEmailAndIsDeleted(String email, Boolean isDeleted);

}