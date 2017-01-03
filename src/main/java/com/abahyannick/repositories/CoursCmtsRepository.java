package com.abahyannick.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abahyannick.models.CoursCmts;

public interface CoursCmtsRepository extends JpaRepository<CoursCmts, Integer>{
	
	List<CoursCmts>  findAll();

	

}
