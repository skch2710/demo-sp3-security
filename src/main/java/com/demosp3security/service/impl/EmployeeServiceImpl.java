package com.demosp3security.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.demosp3security.dao.EmployeeDAO;
import com.demosp3security.dto.EmployeeDetailsDTO;
import com.demosp3security.dto.EmployeeSearch;
import com.demosp3security.dto.Result;
import com.demosp3security.exception.CustomException;
import com.demosp3security.model.Employee;
import com.demosp3security.model.EmployeeRole;
import com.demosp3security.model.Roles;
import com.demosp3security.service.EmployeeService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

	@Autowired
	private EmployeeDAO employeeDAO;

	/** The b crypt password encoder. */
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public Result saveEmployee(EmployeeDetailsDTO details) {
		Result result = null;
		try {
			Employee emailExistEmployee = employeeDAO.findByEmailIdIgnoreCase(details.getEmailId());

			if (emailExistEmployee != null) {
				result = new Result();
				result.setStatusCode(HttpStatus.BAD_REQUEST.value());
				result.setErrorMessage(details.getEmailId() + " is already exist.");
			} else {
				Employee employee = new Employee();
				employee.setEmailId(details.getEmailId());
				employee.setFirstName(details.getFirstName());
				employee.setLastName(details.getLastName());
				String encodedPassword = bCryptPasswordEncoder.encode(details.getPasswordSalt());
//				BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
//			    String encodedPassword = encoder.encode(details.getPasswordSalt());
				employee.setPasswordSalt(encodedPassword);
				employee.setCreatedById(details.getCreatedById());
				employee.setModifiedById(details.getModifiedById());
				employee.setCreatedDate(new Date());
				employee.setModifiedDate(new Date());

				EmployeeRole employeeRole = new EmployeeRole();
				Roles roles = new Roles();
				roles.setRoleId(details.getRoleId());
				employeeRole.setRole(roles);
				employeeRole.setCreatedById(details.getCreatedById());
				employeeRole.setModifiedById(details.getModifiedById());
				employeeRole.setCreatedDate(new Date());
				employeeRole.setModifiedDate(new Date());
				employeeRole.setActive(true);
				employeeRole.setRoleStartDate(new Date());
				employeeRole.setRoleEndDate(null);
				employeeRole.setEmployee(employee);
				
				employee.setEmployeeRole(employeeRole);

				Employee serverEmployee = employeeDAO.save(employee);

				result = new Result(serverEmployee);
				result.setStatusCode(HttpStatus.OK.value());
				result.setSuccessMessage("Employee Saved SuccessFully.");
			}
		} catch (Exception e) {
			log.error("Error in save Employee :: " + e.getMessage());
			throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

	@Override
	public Result searchEmployee(EmployeeSearch search) {

		Result result = null;
		try {
			Employee employee = employeeDAO.findByEmpId(search.getEmpId());

			if (employee == null) {
				result = new Result();
				result.setStatusCode(HttpStatus.NOT_FOUND.value());
				result.setErrorMessage("Not Found.");
			} else {
				result = new Result(employee);
				result.setStatusCode(HttpStatus.OK.value());
				result.setSuccessMessage("Getting Succussfully.");
			}
		} catch (Exception e) {
			log.error("Error in serach ::" + e.getMessage());
			throw new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}

}
