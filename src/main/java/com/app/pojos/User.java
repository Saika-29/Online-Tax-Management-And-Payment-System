package com.app.pojos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;

	private String userName;

	private String password;

	private String role;

	// mapped to Employer table via User object(in Employer Table)
	@OneToOne(mappedBy = "userEmployer")
	private Employer employerDetails;

	// mapped to Employee Table via User object (in Employee Table)
	@OneToOne(mappedBy = "userEmployee")
	private Employee employeeDetails;

	public User() {
		// TODO Auto-generated constructor stub
	}

	public User(String userName, String password, String role) {
		super();
		this.userName = userName;
		this.password = password;
		this.role = role;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Employer getEmployerDetails() {
		return employerDetails;
	}

	public void setEmployerDetails(Employer employerDetails) {
		this.employerDetails = employerDetails;
	}

	public Employee getEmployeeDetails() {
		return employeeDetails;
	}

	public void setEmployeeDetails(Employee employeeDetails) {
		this.employeeDetails = employeeDetails;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", role=" + role + "]";
	}

}
