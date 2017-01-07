package com.abahyannick.repositories;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.abahyannick.DAO.CourArgs;

public interface CourArgsRepository extends JpaRepository<CourArgs, Integer>{
	
 List<CourArgs> findAll();
 
}