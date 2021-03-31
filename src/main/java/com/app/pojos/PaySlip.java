package com.app.pojos;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "payslip")
public class PaySlip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "payslip_id")
	private Integer paySlipId;

	private String month;
	private int year;
	private int attendance;
	private double total;

	@ManyToOne
	@JoinColumn(name = "emp_id")
	private Employee employeePaySlip;

	public PaySlip() {
		// TODO Auto-generated constructor stub
	}

	public PaySlip(String month, int year, int attendance, double total) {
		super();
		this.month = month;
		this.year = year;
		this.attendance = attendance;
		this.total = total;
	}

	public Integer getPaySlipId() {
		return paySlipId;
	}

	public void setPaySlipId(Integer paySlipId) {
		this.paySlipId = paySlipId;
	}

	public String getMonth() {
		return month;
	}

	public void setMonth(String month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getAttendance() {
		return attendance;
	}

	public void setAttendance(int attendance) {
		this.attendance = attendance;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public Employee getEmployeePaySlip() {
		return employeePaySlip;
	}

	public void setEmployeePaySlip(Employee employeePaySlip) {
		this.employeePaySlip = employeePaySlip;
	}

	@Override
	public String toString() {
		return "PaySlip [paySlipId=" + paySlipId + ", month=" + month + ", year=" + year + ", attendance=" + attendance
				+ ", total=" + total + "]";
	}

}
