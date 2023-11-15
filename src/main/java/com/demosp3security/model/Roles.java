package com.demosp3security.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "roles", schema = "login")
public class Roles {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="role_id")
	private Long roleId;

	@Column(name="role_name")
	private String roleName;

	@Column(name="is_active")
	private boolean isActive;

	@Column(name="is_external_role")
	private boolean isExternalRole;

//	private boolean isThisCustomRole;

}
