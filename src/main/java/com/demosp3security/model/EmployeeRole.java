package com.demosp3security.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "emp_roles", schema = "login")
@JsonIgnoreProperties(ignoreUnknown = true, value = { "employee" })
public class EmployeeRole {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="emp_role_id")
	private int empRoleId;

	@OneToOne
	@JoinColumn(name = "emp_id", nullable = true)
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "role_id", nullable = true)
	private Roles role;

	@Column(name="role_start_date")
	private Date roleStartDate;

	@Column(name="role_end_date")
	private Date roleEndDate;

	@Column(name="is_active")
	private boolean isActive;

	@Column(name="created_by_id")
	private Long createdById;

	@Column(name="created_date")
	private Date createdDate;

	@Column(name="modified_by_id")
	private Long modifiedById;

	@Column(name="modified_date")
	private Date modifiedDate;

}
