package com.demosp3security.model;

import java.util.Date;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.Data;

@Entity
@Data
@Table(name="employees",schema="login")
public class Employee{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_id")
	private Long empId;
	
	@Column(name="email_id")
	private String emailId;
	
	@Column(name="first_name")
	private String firstName;
	
	@Column(name="last_name")
	private String lastName;
	
	@Column(name="password_salt")
	private String passwordSalt;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "employee", cascade = CascadeType.ALL)
	private EmployeeRole employeeRole;
	
	@Transient
	private String otp;
	
	@Column(name="created_by_id")
	private Long createdById;
	
	@Column(name="created_date")
	private Date createdDate;
	
	@Column(name="modified_by_id")
	private Long modifiedById;
	
	@Column(name="modified_date")
	private Date modifiedDate;
}
