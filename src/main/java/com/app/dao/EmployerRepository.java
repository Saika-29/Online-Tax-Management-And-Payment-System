package com.app.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.pojos.Employer;

public interface EmployerRepository extends JpaRepository<Employer, Integer> {

	@Query(value = "select * from employer where user_id = ?1", nativeQuery = true)
	Employer findByUserId(int id);

}
