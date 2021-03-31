package com.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.EmployerRepository;
import com.app.pojos.Employer;

@Service
@Transactional
public class EmployerServiceImpl implements IEmployerService {

	@Autowired
	private EmployerRepository employerRepo;

	@Override
	public Employer findByUserId(int id) {

		return employerRepo.findByUserId(id);
	}

}
