package com.abahyannick.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abahyannick.models.Cours;

public interface CoursRepository extends JpaRepository<Cours, Integer> {
	
     List<Cours> findAll();

}
