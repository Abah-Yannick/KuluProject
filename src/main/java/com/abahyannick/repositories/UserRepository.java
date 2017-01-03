package com.abahyannick.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Component;

import com.abahyannick.models.User;

@RepositoryRestResource
@Component
public interface UserRepository extends JpaRepository<User, Integer>{
	
	List<User> findAll();
	User findById(@Param("id")Long id);
	User findByUsernameAndPassword(@Param("username")String username,
			@Param("password")String password);
	
	User findByEmail(@Param("email")String email);
	User findByUsername(@Param("username")String username);

}
