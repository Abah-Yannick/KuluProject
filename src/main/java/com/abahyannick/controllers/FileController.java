package com.abahyannick.controllers;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.http.fileupload.FileUploadBase.FileSizeLimitExceededException;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.JsonWebKeySet;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.MalformedClaimException;
import org.jose4j.jwt.consumer.InvalidJwtException;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.abahyannick.models.FileUpload;
import com.abahyannick.models.User;
import com.abahyannick.repositories.UserRepository;

@CrossOrigin
@RestController
public class FileController {

	private final UserRepository repository;
    
	@Autowired
	public FileController(UserRepository repo) {
		repository = repo;
	}

    static Logger logger = Logger.getLogger(FileController.class);
    
    @RequestMapping(
        value = "/getProfileImage",
        method = RequestMethod.GET
    )
    public ResponseEntity getProfileImage(@RequestParam("username") String username) {

        User user = repository.findByUsername(username);



        return new ResponseEntity(user.getImagePath(), HttpStatus.OK);
    }
    
    @RequestMapping(
            value = "/upload",
            method = RequestMethod.POST
        )
        public ResponseEntity uploadFile(HttpServletRequest request1,@RequestParam("filepath") String filepath) throws FileSizeLimitExceededException, MalformedClaimException{
    	
    	
    	String token = request1.getHeader("Authorization");
      
    	
	    if (token == null) {
		return new ResponseEntity<String>("Access Denied for this functionality !!!", HttpStatus.BAD_REQUEST);
		}

		JsonWebKeySet jwks = new JsonWebKeySet(UserController.jwkList);
		JsonWebKey jwk = jwks.findJsonWebKey("1", null, null, null);
		logger.info("JWK (1) ===> " + jwk.toJson());

		// Validate Token's authenticity and check claims
		JwtConsumer jwtConsumer = new JwtConsumerBuilder()
				.setRequireExpirationTime()
				.setAllowedClockSkewInSeconds(30) // allow for a 30 second difference to account for clock skew 
				.setRequireSubject()
				.setExpectedIssuer("jobert.com")
				.setVerificationKey(jwk.getKey()).build(); // create the JwtConsumer instance
		try {
			// Validate the JWT and process it to the Claims
			JwtClaims jwtClaims = jwtConsumer.processToClaims(token);
			
			logger.info("JWT validation succeeded! " + jwtClaims);
			
			logger.info("upload file.....");
			
			String username =jwtClaims.getStringClaimValue("username");
			User user = repository.findByUsername(username);
			user.setImagePath(filepath);
			user = repository.save(user);
			
			 return new ResponseEntity(user, HttpStatus.OK);
		} catch (InvalidJwtException e) {
			logger.error("JWT is Invalid: " + e);
			return new ResponseEntity<String>("Access Denied for this functionality !!!", HttpStatus.BAD_REQUEST);
		}

    	
    	
    	

           
        }
}
