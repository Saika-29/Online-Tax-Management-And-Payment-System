package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
public class Employer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int employerId;
	private String name;

	@OneToMany(mappedBy = "employerDetails", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Employee> employeeList = new ArrayList<>();
	
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "userId")
    private User userEmployer;

	public Employer() {
		// TODO Auto-generated constructor stub
	}

	public Employer(String name) {
		super();
		this.name = name;
	}

	public int getEmployerId() {
		return employerId;
	}

	public void setEmployerId(int employerId) {
		this.employerId = employerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}

	public User getUserEmployer() {
		return userEmployer;
	}

	public void setUserEmployer(User userEmployer) {
		this.userEmployer = userEmployer;
	}

	@Override
	public String toString() {
		return "Employer [employerId=" + employerId + ", name=" + name + "]";
	}

	// helper methods to extract username and password
	public String showUsername(User userEmployer) {

		String employerUserName = userEmployer.getUserName();

		return employerUserName;
	}

	public String showPassword(User userEmployer) {

		String password = userEmployer.getPassword();

		return password;
	}

	// helper methods to add and remove employees
	public String addEmployee(Employee e) {

		employeeList.add(e);
		e.setStatus(true);

		return "Employee added Successfully";
	}

	public String removeEmployee(Employee e) {

		employeeList.remove(e);
		e.setStatus(false);

		return "Employee removed Successfully";
	}

}
