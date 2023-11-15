package com.demosp3security.service;

import com.demosp3security.dto.EmployeeDetailsDTO;
import com.demosp3security.dto.EmployeeSearch;
import com.demosp3security.dto.Result;

public interface EmployeeService {

	Result saveEmployee(EmployeeDetailsDTO details);
	
	Result searchEmployee(EmployeeSearch search);

}
