package com.app.controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.helper.Message;
import com.app.pojos.Employee;
import com.app.pojos.Employer;
import com.app.pojos.User;
import com.app.service.IEmployeeService;
import com.app.service.IEmployerService;
import com.app.service.ISalaryService;
import com.app.service.IUserService;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private IEmployeeService empService;

	@Autowired
	private IEmployerService adminService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ISalaryService salService;

	// method for adding common data to response
	@ModelAttribute
	public void addCommonData(Model model, Principal principal) {

		User user = userService.getUserByUserName(principal.getName());

		System.out.println("USER " + user);

		Employee loggedInEmployee = empService.getEmployeeByUserId(user.getUserId());

		Employer employerDetails = loggedInEmployee.getEmployerDetails();

		System.out.println("EMPLOYEE : " + loggedInEmployee);

		model.addAttribute("user", user);

		model.addAttribute("loggedInEmployee", loggedInEmployee);

		model.addAttribute("employerDetails", employerDetails);

	}

	// Employee Dashboard
	@GetMapping("/index")
	public String home(Model model) {

		model.addAttribute("title", "Dashboard");

		return "employee/emp_dashboard";
	}

	// employee profile handler
	@GetMapping("/profile")
	public String yourProfile(Model model) {

		model.addAttribute("title", "Profile Page");
		return "employee/profile";
	}

	// open settings handler
//	@GetMapping("/settings")
//	public String openSettings() {
//		return "normal/settings";
//	}// change password..handler
//
//	@PostMapping("/change-password")
//	public String changePassword(@RequestParam("oldPassword") String oldPassword,
//			@RequestParam("newPassword") String newPassword, Principal principal, HttpSession session) {
//		System.out.println("OLD PASSWORD " + oldPassword);
//		System.out.println("NEW PASSWORD " + newPassword);
//
//		String userName = principal.getName();
//		User currentUser = this.userRepository.getUserByUserName(userName);
//		System.out.println(currentUser.getPassword());
//
//		if (this.bCryptPasswordEncoder.matches(oldPassword, currentUser.getPassword())) {
//			// change the password
//
//			currentUser.setPassword(this.bCryptPasswordEncoder.encode(newPassword));
//			this.userRepository.save(currentUser);
//			session.setAttribute("message", new Message("Your password is successfully changed..", "success"));
//
//		} else {
//			// error...
//			session.setAttribute("message", new Message("Please Enter correct old password !!", "danger"));
//			return "redirect:/user/settings";
//		}
//
//		return "redirect:/user/index";
//	}
//

	// open update form handler
	@GetMapping("/show-update-form")
	public String updateForm(Model m) {

		m.addAttribute("title", "Update Employee");

		Employee employee = (Employee) m.getAttribute("loggedInEmployee");

		System.out.println("employee : " + employee);

		m.addAttribute("employee", employee);

		return "employee/update_form";
	}

	// update employee handler
	@PostMapping("/process-update")
	public String updateHandler(@ModelAttribute("employee") Employee employee, Model m, HttpSession session,
			Principal principal) {

		try {

			System.out.println("updated employee : " + employee);

			// get user details from model
			User user = (User) m.getAttribute("user");

			// get employer details from model
			Employer employerDetails = (Employer) m.getAttribute("employerDetails");

			System.out.println("user : " + user);

			// set user details in employee
			employee.setUserEmployee(user);

			// set Employer id in employee
			employee.setEmployerDetails(employerDetails);

			// set salary details
			Employee employee2 = empService.setSalaryDetails(employee);

			// now save the employee in database
			Employee updatedEmployee = empService.saveEmployee(employee2);

			System.out.println("Updated Employee details : " + updatedEmployee);

			session.setAttribute("message", new Message("Your Record has been updated...", "success"));

		} catch (Exception e) {
			System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();
			// message error
			session.setAttribute("message", new Message("Something went wrong !! Try again..", "danger"));
		}

		return "redirect:/employee/show-update-form";
	}
}
