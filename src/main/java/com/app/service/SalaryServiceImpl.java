package com.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.SalaryRepository;
import com.app.pojos.Salary;

@Service
@Transactional
public class SalaryServiceImpl implements ISalaryService {

	@Autowired
	private SalaryRepository salRepo;

	@Override
	public List<String> getAllDesignation() {

		List<Salary> data = salRepo.findAll();

		List<String> desg = data.stream().map(Salary::getDesg).collect(Collectors.toList());

		return desg;
	}

	@Override
	public List<Salary> findAll() {

		return salRepo.findAll();
	}

	@Override
	public Salary getSalaryByDesignation(String designation) {

		return salRepo.findByDesg(designation);
	}

}
