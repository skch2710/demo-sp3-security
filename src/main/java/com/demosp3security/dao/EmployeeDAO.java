package com.demosp3security.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.demosp3security.model.Employee;

public interface EmployeeDAO extends JpaRepository<Employee, Long> {

	Employee findByEmailId(String emailId);
	
	Employee findByEmailIdIgnoreCase(String emailId);
	
	Employee findByEmpId(Long empId);

}
