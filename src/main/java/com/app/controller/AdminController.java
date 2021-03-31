package com.app.controller;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.app.dao.EmployeeRepository;
import com.app.dao.EmployerRepository;
import com.app.dao.SalaryRepository;
import com.app.dao.UserRepository;
import com.app.helper.Message;
import com.app.pojos.Employee;
import com.app.pojos.Employer;
import com.app.pojos.PaySlip;
import com.app.pojos.Salary;
import com.app.pojos.User;
import com.app.service.IEmployeeService;
import com.app.service.IEmployerService;
import com.app.service.ISalaryService;
import com.app.service.IUserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

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

		Employer employer = adminService.findByUserId(user.getUserId());

		System.out.println("EMPLOYER : " + employer);

		model.addAttribute("user", user);

		model.addAttribute("employer", employer);

	}

	// ADMIN Dashboard
	@GetMapping("/index")
	public String home(Model model) {

		model.addAttribute("title", "ADMIN Dashboard");

		return "admin/admin_dashboard";
	}

	// show Add Emplyees form handler

	@GetMapping("/add-employee")
	public String showAddEmployeeForm(Model model) {

		model.addAttribute("title", "Add Employee");

		model.addAttribute("employee", new Employee());

		List<String> listDesg = salService.getAllDesignation();

		System.out.println(listDesg);

		model.addAttribute("listDesg", listDesg);

		model.addAttribute("user1", new User());

		return "admin/add_employee_form";
	}

	// processing Add Employee form

	@PostMapping("/process-add-employee")
	public String processAddEmployee(@ModelAttribute("employee") Employee employee, @ModelAttribute("user1") User user,
			BindingResult result, Principal principal, HttpSession session, Model model) {

		try {

			// for debugging purposes
			System.out.println(employee);

			System.out.println(user);

			Employer employer = (Employer) model.getAttribute("employer");

			System.out.println(employer);

			// save user details in user table
			User savedUser = userService.saveCredentials(user);

			// set employer details in employee
			employee.setEmployerDetails(employer);

			// set user details in employee
			employee.setUserEmployee(savedUser);

			// set salary details in employee
			Employee employee2 = empService.setSalaryDetails(employee);

			// save employee details in employee table
			Employee savedEmployee = empService.saveEmployee(employee2);

			// show registered employee data : for debugging purposes
			System.out.println("DATA : " + savedEmployee);

			System.out.println("Added to database");

			// Success Message
			session.setAttribute("message", new Message("Your Employee is added !! Add more..", "success"));

		} catch (Exception e) {

			System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();
			// message error
			session.setAttribute("message", new Message("Something went wrong !! Try again..", "danger"));

		}

		return "admin/add_employee_form";
	}

	// list employees handler
	// per page = 5[n]
	// current page = 0 [page]
	@GetMapping("/list-employees/{page}")
	public String showEmployees(@PathVariable("page") Integer page, Model m) {

		// send the title in model attributes
		m.addAttribute("title", "All Employees");

		// extract employer info
		Employer employer = (Employer) m.getAttribute("employer");

		System.out.println(employer);

		List<Employee> employeeList = employer.getEmployeeList();

		// for debugging
		System.out.println(employeeList);

		// pageable contains two info
		// 1. current page -- page variable
		// 2. employees per page ---- 5
		Pageable pageable = PageRequest.of(page, 5);

		Page<Employee> allEmployees = empService.getAllEmployees(pageable);

		m.addAttribute("allEmployees", allEmployees);
		m.addAttribute("currentPage", page);
		m.addAttribute("totalPages", allEmployees.getTotalPages());

		return "admin/show_employees";
	}

	// delete contact handler

	@GetMapping("/remove-employee/{empId}")
	public String removeEmployees(@PathVariable("empId") Integer empId, Model model, HttpSession session,
			Principal principal) {

		System.out.println("Emp ID " + empId);

		String exEmpName = empService.removeEmployee(empId);

		System.out.println(exEmpName + " is removed successfully !!!");

		session.setAttribute("message", new Message(exEmpName + " is removed successfully !!!", "success"));

		return "redirect:/admin/list-employees/0";
	}

	// search employee

	@RequestMapping("/salary/{empId}")
	public String payment(@PathVariable("empId") Integer empId, Model model, HttpSession session, Principal principal) {

		Employee employee = empService.findById(empId);

		model.addAttribute("employee", employee);

		model.addAttribute("payslip", new PaySlip());

		model.addAttribute("title", "Payment");

		return "admin/salary";
	}

	

	@RequestMapping("/credit-salary/{empId}")
	public String creditSalary(@PathVariable("empId") Integer empId, @ModelAttribute("payslip") PaySlip paySlip,
			BindingResult result, Model model, HttpSession session) {

		try {
			System.out.println("empId : " + empId);

			System.out.println("paySlip : " + paySlip);

			PaySlip calculateSalary = empService.calculateSalary(paySlip, empId);

			System.out.println("Result : " + calculateSalary);

			model.addAttribute("salary_details", calculateSalary);

			session.setAttribute("message", new Message("Salary has been credited !!!", "success"));

		} catch (Exception e) {

			System.out.println("ERROR " + e.getMessage());
			e.printStackTrace();
			// message error
			session.setAttribute("message", new Message("Something went wrong !! Try again..", "danger"));

		}

		return "admin/salary";
	}
	
	@GetMapping("/settings")
	public String showSalaryTable(Model model) {

		model.addAttribute("title", "Settings");
		
		model.addAttribute("salary", new Salary());

		return "admin/settings";

	}

}
