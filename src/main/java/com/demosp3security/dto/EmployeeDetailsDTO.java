package com.demosp3security.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper=false)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDetailsDTO{
	
	private Long empId;
	
	private String emailId;
	
	private String firstName;
	
	private String lastName;
	
	private String passwordSalt;
	
	private Long roleId;
	
	private Long createdById;
	
	private Long modifiedById;
	
}
