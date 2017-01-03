package com.abahyannick.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.abahyannick.models.CoursCmts;
import com.abahyannick.models.ReplyCmts;
import com.abahyannick.repositories.CoursCmtsRepository;
import com.abahyannick.repositories.ReplyCmtsRepository;


@Component
@RepositoryRestController
@RequestMapping("/userCmts")
public class CoursCmtsController {
	
	private final CoursCmtsRepository repository;
	private final ReplyCmtsRepository repository1;
    
	@Autowired
	public CoursCmtsController(CoursCmtsRepository repo,ReplyCmtsRepository repo1) {
		repository = repo;
		repository1 = repo1;
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/cmt")
	public @ResponseBody ResponseEntity<?> saveCmt(HttpServletRequest request,
			@RequestParam("cText") char[] cText,@RequestParam("coursArgsId") Integer coursArgsId,@RequestParam("userId") Integer userId)
	throws IOException {
		
		Long cWhen = System.currentTimeMillis();
		String cIp = request.getHeader("X-FORWARDED-FOR");
		if (cIp == null) {
			cIp = request.getRemoteAddr();
		}
		
		CoursCmts coursCmts = new CoursCmts(cText,  cIp, cWhen, coursArgsId,  userId);
		
		coursCmts = repository.save(coursCmts);

			return new ResponseEntity(coursCmts, HttpStatus.OK);
			
		}
	
	@RequestMapping(method = RequestMethod.POST, value = "/replycmt")
	public @ResponseBody ResponseEntity<?> saveReplyCmt(HttpServletRequest request,
			@RequestParam("cText") char[] cText,@RequestParam("coursArgsId") Integer coursArgsId,@RequestParam("cId") Integer cId,
			@RequestParam("userReplyId") Integer userReplyId,@RequestParam("userPostId") Integer userPostId)
	throws IOException {
		
		Long cWhen = System.currentTimeMillis();
		String cIp = request.getHeader("X-FORWARDED-FOR");
		if (cIp == null) {
			cIp = request.getRemoteAddr();
		}
		
		ReplyCmts replyCmts = new ReplyCmts(cText,  cIp, cWhen,cId, coursArgsId,userReplyId,  userPostId);
		
		 repository1.save(replyCmts);

			return new ResponseEntity(replyCmts, HttpStatus.OK);
			
		}

			
	}


