package com.app.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.app.pojos.Employee;
import com.app.pojos.PaySlip;

public interface IEmployeeService {
	
	Employee getEmployeeByUserId(int id);
	
	Employee findById(int id);
	
	Employee saveEmployee(Employee emp);
	
	Employee setSalaryDetails(Employee emp);
	
	Page<Employee> getAllEmployees(Pageable pA);
	
	String removeEmployee(int id);
	
	List<Employee> findByNameIgnoreCase(String query);
	
	PaySlip calculateSalary(PaySlip paySlip, Integer empId);

}
