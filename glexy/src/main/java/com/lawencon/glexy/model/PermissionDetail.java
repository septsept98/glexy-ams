package com.lawencon.glexy.model;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.lawencon.base.BaseEntity;

@Entity
@Table(name = "permission_details")
public class PermissionDetail extends BaseEntity {
	
	private static final long serialVersionUID = -7585163916237895433L;

	@ManyToOne
	@JoinColumn(name = "roles_id", columnDefinition = "varchar")
	private Roles rolesId;
	
	@ManyToOne
	@JoinColumn(name = "permissions_id", columnDefinition = "varchar")
	private Permissions permissionsId;

	public Roles getRolesId() {
		return rolesId;
	}

	public void setRolesId(Roles rolesId) {
		this.rolesId = rolesId;
	}

	public Permissions getPermissionsId() {
		return permissionsId;
	}

	public void setPermissionsId(Permissions permissionsId) {
		this.permissionsId = permissionsId;
	}
	
}
