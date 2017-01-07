package com.abahyannick.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.abahyannick.DAO.Cours;
import com.abahyannick.repositories.CoursRepository;

@Component
@RepositoryRestController
@RequestMapping("/Cours")
public class CoursController {
	
	private final CoursRepository coursRepo;

	@Autowired
	public CoursController(CoursRepository coursRepo) {
		this.coursRepo = coursRepo;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/coursAll")
	public ResponseEntity<Object> listCourses(){
		List<Cours> cours = coursRepo.findAll();
		
       
		return new ResponseEntity(cours, HttpStatus.OK);


			
	}
	
	

}
