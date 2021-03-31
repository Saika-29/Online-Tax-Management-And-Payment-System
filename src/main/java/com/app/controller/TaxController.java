package com.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/tax")
public class TaxController {
	
	
	@GetMapping("/index")
	public String home(Model model) {

		model.addAttribute("title", "Dashboard");

		return "tax/tax_dashboard";
	}
	
//	// method for adding common data to response
//		@ModelAttribute
//		public void addCommonData(Model model, Principal principal) {
//			String userName = principal.getName();
//			System.out.println("USERNAME " + userName);
//
//			// get the user using username(Email)
//
//			User user = userRepository.getUserByUserName(userName);
//			System.out.println("USER " + user);
//			model.addAttribute("user", user);
//
//		}

}
