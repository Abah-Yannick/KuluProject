package com.abahyannick.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;


import com.abahyannick.DAO.FileUpload;
import com.abahyannick.DAO.User;
import com.abahyannick.repositories.UserRepository;
import com.abahyannick.services.UserService;
import com.abahyannick.utils.SendMail;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;


@Component
@RepositoryRestController
@RequestMapping("/user")
public class UserController {

	private final UserRepository repository;
	private final Integer AUTH_INDEX = 0;
    
	@Autowired
	public UserController(UserRepository repo) {
		repository = repo;
	}
	
	@Autowired
	UserService userService;
	
	@Autowired
	SendMail sendMail;

    private MailSender mailSender;
    private SimpleMailMessage templateMessage;

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void setTemplateMessage(SimpleMailMessage templateMessage) {
        this.templateMessage = templateMessage;
    }
    
	@Value("${smtp.host}")
	private String host;
	
	@Value("${smtp.port}")
	private String port;
	
	@Value("${smtp.user}")
	private String username;
	
	@Value("${smtp.pass}")
	private String pass;
	


    
	static Logger logger = Logger.getLogger(UserController.class);
	static List<JsonWebKey> jwkList = null;

	static {
		logger.info("Inside static initializer...");
		jwkList = new LinkedList<JsonWebKey>();
		// Creating three keys, will use one now, maybe rework this to be more
		// flexible -- if time permits
		for (int kid = 1; kid <= 3; kid++) {
			JsonWebKey jwk = null;
			try {
				jwk = RsaJwkGenerator.generateJwk(2048);
				logger.info("PUBLIC KEY (" + kid + "): " + jwk.toJson(JsonWebKey.OutputControlLevel.PUBLIC_ONLY));
			} catch (JoseException e) {
				e.printStackTrace();
			}
			jwk.setKeyId(String.valueOf(kid));
			jwkList.add(jwk);
		}

	}

	@RequestMapping(method = RequestMethod.GET, value = "/authenticate")
	public @ResponseBody ResponseEntity<?> authenticateCredentials(@RequestParam("password") String password,
			@RequestParam("username") String username) throws JsonGenerationException, JsonMappingException, IOException {

		logger.info("Authenticating User Credentials...");

		if (username == null||password == null) {
			return new ResponseEntity<String>("Username or Password value is missing!!!", HttpStatus.BAD_REQUEST);
		}

		User user = null;
		FileUpload file = null;
		user = repository.findByUsername(username);
		

	    if (user!=null && ((userService.checkPassword(password, user.getPassword()))||(user.getPassword().compareTo(password))==0)){
	    	
	    	String jwt = userService.tokenGeneretor(jwkList,AUTH_INDEX,user);

		return new ResponseEntity<String>(jwt, HttpStatus.OK);

	    }
	    return new ResponseEntity<String>("User not logged in !!!", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public @ResponseBody ResponseEntity<?> registerUser(
			@RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,@RequestParam("password") String password,WebRequest request)
			
				throws IOException {
		String appUrl = request.getContextPath();
		User user1 = repository.findByUsername(username);
		if(user1==null){

		User user = new User(email, username, firstname, lastname, password);
		user.setPassword(userService.hashPassword(user.getPassword()));
		user = repository.save(user);
		confirmRegistration(user, appUrl);
		
		if (user.getEmail() == email) {
			return new ResponseEntity(user, HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("User not Created!!!", HttpStatus.BAD_REQUEST);
		}
		}else{
			return new ResponseEntity<String>("User already exists.", HttpStatus.BAD_REQUEST);
		}

			
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/checkemail")
	public @ResponseBody ResponseEntity<?> checkUserByEmail(@RequestParam("email") String email) {
		logger.info("Check user email....");
		User result = null;
	    result = repository.findByEmail(email);
		if(result==null){
			logger.info("Email valide.");
			
			return new ResponseEntity<String>("Email valide.", HttpStatus.OK);
		}else{
			return new ResponseEntity<String>("Email already exists.", HttpStatus.BAD_REQUEST);
		}
	}
	

	@RequestMapping(method = RequestMethod.GET, value = "/checkusername")
	public @ResponseBody ResponseEntity<?> checkUserByUsername(@RequestParam("username") String username) {
		logger.info("Check user email....");
		
		User result = null;
		result = repository.findByUsername(username);
		if(result==null){
			logger.info("Valide Username");
			
			return new ResponseEntity<String>("Valide Username", HttpStatus.OK);
		}else{
			logger.info("User already exists.");
			return new ResponseEntity<String>("User already exists.", HttpStatus.BAD_REQUEST);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/activeUser")
	public void activeUser(@RequestParam("username") String username) {
 
	}
	
	
	   private void confirmRegistration(User user,String appUrl) {
	         
	        String token = UUID.randomUUID().toString();
	        userService.createVerificationToken(user, token);
	        String recipientAddress = user.getEmail();
	        
	        String subject = "Registration Confirmation";
	        String confirmationUrl 
	          = appUrl + "/registrationConfirm?token=" + token;
	        sendMail.sendMail(recipientAddress, subject, confirmationUrl);

	    }
	   
	  


}
