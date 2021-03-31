package com.app.pojos;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Salary {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int salaryId;

	private String desg;
	private double basic;
	private double da;
	private double hra;
	private double lta;

	@OneToMany(mappedBy = "salary", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
	private List<Employee> empSalary = new ArrayList<>();

	public Salary() {
		// TODO Auto-generated constructor stub
	}

	public Salary(String desg, double basic, double da, double hra, double lta) {
		super();
		this.desg = desg;
		this.basic = basic;
		this.da = da;
		this.hra = hra;
		this.lta = lta;
	}

	public int getSalaryId() {
		return salaryId;
	}

	public void setSalaryId(int salaryId) {
		this.salaryId = salaryId;
	}

	public String getDesg() {
		return desg;
	}

	public void setDesg(String desg) {
		this.desg = desg;
	}

	public double getBasic() {
		return basic;
	}

	public void setBasic(double basic) {
		this.basic = basic;
	}

	public double getDa() {
		return da;
	}

	public void setDa(double da) {
		this.da = da;
	}

	public double getHra() {
		return hra;
	}

	public void setHra(double hra) {
		this.hra = hra;
	}

	public double getLta() {
		return lta;
	}

	public void setLta(double lta) {
		this.lta = lta;
	}

	public List<Employee> getEmpSalary() {
		return empSalary;
	}

	public void setEmpSalary(List<Employee> empSalary) {
		this.empSalary = empSalary;
	}

	@Override
	public String toString() {
		return "Salary [salaryId=" + salaryId + ", desg=" + desg + ", basic=" + basic + ", da=" + da + ", hra=" + hra
				+ ", lta=" + lta + "]";
	}

}
