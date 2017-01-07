package com.abahyannick.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abahyannick.DAO.ReplyCmts;

public interface ReplyCmtsRepository extends JpaRepository<ReplyCmts, Integer>{

	
	List<ReplyCmts> findAll();
}
