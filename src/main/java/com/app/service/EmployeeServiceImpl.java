package com.app.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.dao.EmployeeRepository;
import com.app.dao.PaySlipRepository;
import com.app.pojos.Employee;
import com.app.pojos.PaySlip;
import com.app.pojos.Salary;

@Service
@Transactional
public class EmployeeServiceImpl implements IEmployeeService {

	@Autowired
	private EmployeeRepository empRepo;

	@Autowired
	private ISalaryService salService;

	@Autowired
	private IUserService userService;
	
	@Autowired
	private PaySlipRepository paySlipRepo;

	@Override
	public Employee saveEmployee(Employee emp) {

		// for debugging purposes
		System.out.println(emp);

		// set employee status to true
		emp.setStatus(true);

		return empRepo.save(emp);
	}

	@Override
	public Employee setSalaryDetails(Employee emp) {

		String designation = emp.getDesignation();

		Salary sal = salService.getSalaryByDesignation(designation);

		emp.setSalary(sal);

		return emp;
	}

	@Override
	public Page<Employee> getAllEmployees(Pageable pA) {

		return empRepo.findByStatusTrue(pA);
	}

	@Override
	public String removeEmployee(int id) {

		Optional<Employee> optionalEmp = empRepo.findById(id);

		Employee employee = optionalEmp.orElseThrow(() -> new UsernameNotFoundException("Employee not Found !!!"));

		// we are not deleting the record of employee,
		// we are simply removing all connections

		// set status of employee to NOT ACTIVE
		employee.setStatus(false);

		// remove salary_id
		employee.setSalary(null);

		// before removing user_id, we also need to remove employee credentials from
		// User table
		// so get user_id first and then delete the record in admin controller
		Integer userId = employee.getUserEmployee().getUserId();

		// remove user_id
		employee.setUserEmployee(null);

		// remove employer id
		employee.setEmployerDetails(null);

		String exEmployeeName = employee.getName();

		// now call the user service to delete employee credentials
		userService.removeCredentials(userId);

		// update the employee by using the save method
		// record will be still there but status and all connections will be null
		empRepo.save(employee);

		return exEmployeeName;
	}

	@Override
	public Employee getEmployeeByUserId(int id) {

		return empRepo.findByUserId(id);
	}

	@Override
	public List<Employee> findByNameIgnoreCase(String query) {

		return empRepo.findByNameIgnoreCase(query);
	}

	@Override
	public Employee findById(int id) {

		Optional<Employee> optionalEmployee = empRepo.findById(id);

		Employee employee = optionalEmployee.get();

		return employee;
	}

	@Override
	public PaySlip calculateSalary(PaySlip paySlip, Integer empId) {

		System.out.println(paySlip);

		Employee employee = empRepo.findById(empId).get();
		
		int attendance = paySlip.getAttendance();

		double basic = employee.getSalary().getBasic();

		double da = employee.getSalary().getDa();

		double hra = employee.getSalary().getHra();

		double lta = employee.getSalary().getLta();
		
		double perDay = (basic+da+hra+lta)/LocalDate.now().lengthOfMonth();
		
		double total = perDay*attendance;

		paySlip.setTotal(total);
		
		paySlip.setMonth(LocalDate.now().getMonth().toString());
		
		paySlip.setYear(LocalDate.now().getYear());
		
		paySlip.setEmployeePaySlip(employee);

		return paySlipRepo.save(paySlip);
	}

}
