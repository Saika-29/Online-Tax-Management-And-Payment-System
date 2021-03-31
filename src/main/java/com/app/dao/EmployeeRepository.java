package com.app.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

	// for future use, when adding multiple employers
	// List<Employee> findEmployeesByEmployerId(int id);

	// get Employee by user_id
	@Query(value = "select * from employees where user_id = ?1", nativeQuery = true)
	Employee findByUserId(int id);

	// Pagination
	// list all employees who are active
	// pageable contains two info
	// 1. current page -- page variable
	// 2. employees per page ---- 5
	Page<Employee> findByStatusTrue(Pageable pageable);

	// for search, pass the query which you have recieved in search bar and return
	// the list of Employees
	List<Employee> findByNameIgnoreCase(String query);

}
