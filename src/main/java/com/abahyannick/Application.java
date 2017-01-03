package com.abahyannick;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

import com.abahyannick.controllers.UserController;
import com.abahyannick.repositories.UserRepository;

@Configuration
@ComponentScan
@EnableJpaRepositories
@Import(RepositoryRestMvcConfiguration.class)
@EnableAutoConfiguration
@PropertySource("application.properties")
public class Application {
//metodo per start dell'applicativo
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		
	}

}