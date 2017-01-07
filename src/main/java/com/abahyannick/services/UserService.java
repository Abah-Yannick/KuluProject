package com.abahyannick.services;

import java.util.List;

import org.apache.log4j.Logger;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jws.JsonWebSignature;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.abahyannick.DAO.User;
import com.abahyannick.DAO.VerificationToken;
import com.abahyannick.repositories.UserRepository;
import com.abahyannick.repositories.VerificationTokenRepository;
import com.abahyannick.utils.BCrypt;


@Component
public  class UserService {
	Logger logger = Logger.getLogger(UserService.class);
	
    @Autowired
    private  VerificationTokenRepository tokenRepository;
    
    @Autowired
    private UserRepository repository;
	
	
	public  String hashPassword(String plaintext) {
		return BCrypt.hashpw(plaintext, BCrypt.gensalt());
	}
	
	public  boolean checkPassword(String password , String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);  
	}
	
	public  String tokenGeneretor(List<JsonWebKey> jwkList, Integer index, User user){
		RsaJsonWebKey senderJwk = (RsaJsonWebKey) jwkList.get(index);
		index++;
        senderJwk.setKeyId(index.toString());
		logger.info("JWK ("+index+") ===> " + senderJwk.toJson());

		// Create the Claims, which will be the content of the JWT
		JwtClaims claims = new JwtClaims();
		claims.setIssuer("jobert.com"); // who creates the token and signs it
		if(index==1){
		claims.setExpirationTimeMinutesInTheFuture(20); 
		}                                               // token will expire (20
														// minutes from now)
		if(index==2){
		claims.setExpirationTimeMinutesInTheFuture(60*24); 
		} 
		
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
		
		if(index==1){
		claims.setClaim("id", user.getId());
		}

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
		return jwt;
		
	}
	

	
	public  void createVerificationToken(User user, String token) {
        VerificationToken myToken = new VerificationToken(token, user);
        tokenRepository.save(myToken);
    }
}
