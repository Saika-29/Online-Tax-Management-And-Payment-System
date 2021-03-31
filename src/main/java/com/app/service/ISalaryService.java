package com.app.service;

import java.util.List;

import com.app.pojos.Salary;

public interface ISalaryService {
	
	List<String> getAllDesignation();
	
	List<Salary> findAll();
	
	Salary getSalaryByDesignation(String designation);

}
