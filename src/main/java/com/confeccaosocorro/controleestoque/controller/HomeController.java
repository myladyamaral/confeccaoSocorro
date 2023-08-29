package com.confeccaosocorro.controleestoque.controller;

import java.util.Locale;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {



	@RequestMapping("/")
	public String index(Model model, Locale locale) {
		
		return "index";
	}

	
}
