package com.abahyannick.controllers;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Component
@Controller
public class HomeController {

	@RequestMapping("/home")
	public String home() {
		return "index";
	}

}
