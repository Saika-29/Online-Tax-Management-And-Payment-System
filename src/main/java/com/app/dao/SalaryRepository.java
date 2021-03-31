package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.pojos.Salary;

public interface SalaryRepository extends JpaRepository<Salary, Integer> {
	
	Salary findByDesg(String desg);

}
