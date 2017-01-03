package com.abahyannick.controllers;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jwk.RsaJwkGenerator;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abahyannick.models.FileUpload;
import com.abahyannick.models.User;
import com.abahyannick.repositories.FileUploadRepository;
import com.abahyannick.repositories.UserRepository;
import com.abahyannick.services.PasswordService;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;


@Component
@RepositoryRestController
@RequestMapping("/user")
public class UserController {

	private final UserRepository repository;
    
	@Autowired
	public UserController(UserRepository repo) {
		repository = repo;
	}
	
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
			@RequestParam("username") String username)

			throws JsonGenerationException, JsonMappingException, IOException {

		logger.info("Authenticating User Credentials...");

		if (username == null||password == null) {
			return new ResponseEntity<String>("Username or Password value is missing!!!", HttpStatus.BAD_REQUEST);
		}

		User user = null;
		FileUpload file = null;
		user = repository.findByUsername(username);
		

	    if (user!=null && ((PasswordService.checkPassword(password, user.getPassword()))||(user.getPassword().compareTo(password))==0)){

		RsaJsonWebKey senderJwk = (RsaJsonWebKey) jwkList.get(0);

		senderJwk.setKeyId("1");
		logger.info("JWK (1) ===> " + senderJwk.toJson());

		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("jobert.com"); // who creates the token and signs it
		claims.setExpirationTimeMinutesInTheFuture(20); // token will expire (20
														// minutes from now)
		claims.setGeneratedJwtId(); // a unique identifier for the token
		claims.setIssuedAtToNow(); // when the token was issued/created (now)
		claims.setNotBeforeMinutesInThePast(1); // time before which the token
												// is not yet valid (2 minutes
												// ago)
		claims.setSubject(user.getUsername()); // the subject/principal is whom
												// the token is about
		// claims.setStringListClaim("roles", user.getRolesList()); //
		// multi-valued claims for roles
		claims.setStringClaim("username", user.getUsername());
		claims.setStringClaim("email", user.getEmail());
		claims.setStringClaim("image",user.getImagePath());
		claims.setStringClaim("firstname", user.getFirstname());
		claims.setStringClaim("lastname", user.getLastname());
		claims.setClaim("id", user.getId());

		JsonWebSignature jws = new JsonWebSignature();

		jws.setPayload(claims.toJson());

		jws.setKeyIdHeaderValue(senderJwk.getKeyId());
		jws.setKey(senderJwk.getPrivateKey());

		jws.setAlgorithmHeaderValue(AlgorithmIdentifiers.RSA_USING_SHA256);

		String jwt = null;
		try {
			jwt = jws.getCompactSerialization();
			logger.info(jwt);
		} catch (JoseException e) {
			e.printStackTrace();
		}

		return new ResponseEntity<String>(jwt, HttpStatus.OK);
		// return ResponseEntity.ok(jwt);
	    }
	    return new ResponseEntity<String>("User not logged in !!!", HttpStatus.BAD_REQUEST);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/register")
	public @ResponseBody ResponseEntity<?> registerUser(
			@RequestParam("email") String email,@RequestParam("username") String username,@RequestParam("firstname") String firstname,
			@RequestParam("lastname") String lastname,@RequestParam("password") String password)
			
				throws IOException {
		User user1 = repository.findByUsername(username);
		if(user1==null){

		User user = new User(email, username, firstname, lastname, password);
		user.setPassword(PasswordService.hashPassword(user.getPassword()));
		user = repository.save(user);
		
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

}
