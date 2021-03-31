package com.app.pojos;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "employees")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int empId;
	@Column
	private String name;
	
	private boolean status;
	private boolean taxStatus;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfBirth;

	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate dateOfJoin;

	private String gender;

	private String designation;

	private String city;
	private String state;
	private String pincode;

	// mapped to Salary table via Employee object
	@ManyToOne
	@JoinColumn(name = "salary_id")
	private Salary salary;
	
	@OneToMany(mappedBy = "employeePaySlip", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<PaySlip> paySlip = new ArrayList<>();

	// mapped to Employer table via employer id
	// use to extract employer details (only name)
	@ManyToOne
	@JoinColumn(name = "employer_id")
	@JsonIgnore
	private Employer employerDetails;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", referencedColumnName = "userId")
	private User userEmployee;

	public Employee() {
		// TODO Auto-generated constructor stub
	}

	public Employee(String name, boolean status, boolean taxStatus, LocalDate dateOfBirth, LocalDate dateOfJoin,
			String gender, String designation, String city, String state, String pincode) {
		super();
		this.name = name;
		this.status = status;
		this.taxStatus = taxStatus;
		this.dateOfBirth = dateOfBirth;
		this.dateOfJoin = dateOfJoin;
		this.gender = gender;
		this.designation = designation;
		this.city = city;
		this.state = state;
		this.pincode = pincode;
	}

	public int getEmpId() {
		return empId;
	}

	public void setEmpId(int empId) {
		this.empId = empId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public boolean isTaxStatus() {
		return taxStatus;
	}

	public void setTaxStatus(boolean taxStatus) {
		this.taxStatus = taxStatus;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public LocalDate getDateOfJoin() {
		return dateOfJoin;
	}

	public void setDateOfJoin(LocalDate dateOfJoin) {
		this.dateOfJoin = dateOfJoin;
	}

	public Employer getEmployerDetails() {
		return employerDetails;
	}

	public void setEmployerDetails(Employer employerDetails) {
		this.employerDetails = employerDetails;
	}

	public Salary getSalary() {
		return salary;
	}

	public void setSalary(Salary salary) {
		this.salary = salary;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public User getUserEmployee() {
		return userEmployee;
	}

	public void setUserEmployee(User userEmployee) {
		this.userEmployee = userEmployee;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", name=" + name + ", status=" + status + ", taxStatus=" + taxStatus
				+ ", dateOfBirth=" + dateOfBirth + ", dateOfJoin=" + dateOfJoin + ", gender=" + gender
				+ ", designation=" + designation + ", city=" + city + ", state=" + state + ", pincode=" + pincode + "]";
	}

}
