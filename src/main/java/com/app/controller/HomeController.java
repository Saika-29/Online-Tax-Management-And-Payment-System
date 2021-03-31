package com.app.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.dao.EmployerRepository;
import com.app.dao.UserRepository;
import com.app.helper.Message;
import com.app.pojos.Employer;
import com.app.pojos.User;

@Controller
public class HomeController {

	@Autowired
	public RestTemplate restTemplate;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmployerRepository employerRepo;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@RequestMapping("/")
	public String home(Model model) {

		model.addAttribute("title", "Home");

		return "home";
	}

	@RequestMapping("/about")
	public String about(Model model) {

		model.addAttribute("title", "About");

		return "about";
	}
	
	@RequestMapping("/redirect")
	public String redirect(Model model) {
		
		model.addAttribute("title", "Redirect");
		
		return "redirect";
	}

	// handler for custom login
	@GetMapping("/signin")
	public String customLogin(Model model) {

		model.addAttribute("title", "Login Page");
		return "login";
	}

	// register user in User Table

	@RequestMapping("/signup")
	public String signup(Model model) {

		model.addAttribute("title", "Register - Smart Contact Manager");
		model.addAttribute("user", new User());
		return "signup";
	}

	@RequestMapping("/register")
	public String registerUser(@ModelAttribute("user") User user, BindingResult result, Model model,
			HttpSession session) {

		try {

			if (result.hasErrors()) {
				System.out.println("ERROR " + result.toString());
				model.addAttribute("user", user);
				return "signup";
			}

			user.setRole("ROLE_ADMIN");
			user.setPassword(passwordEncoder.encode(user.getPassword()));

			System.out.println("USER : " + user);

			userRepo.save(user);

			model.addAttribute("user", new User());

			session.setAttribute("message", new Message("Successfully Registered !!", "alert-success"));

			Employer employer = new Employer();
			employer.setUserEmployer(user);
			employer.setName("IACSD");

			employerRepo.save(employer);

			return "signup";

		} catch (Exception e) {

			e.printStackTrace();
			model.addAttribute("user", user);
			session.setAttribute("message", new Message("Something Went wrong !! " + e.getMessage(), "alert-danger"));
			return "signup";
		}
	}

}
