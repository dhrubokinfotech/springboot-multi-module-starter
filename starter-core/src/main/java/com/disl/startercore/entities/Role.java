package com.disl.startercore.entities;

import com.disl.commons.constants.CommonConstants;
import com.disl.commons.models.AuditModel;
import com.disl.startercore.enums.RoleType;
import com.disl.startercore.constants.AppTables;
import com.disl.startercore.constants.AppTables.RoleTable;
import jakarta.persistence.*;

import java.util.Collection;

@Entity
@Table(name = AppTables.ROLE_NAME)
public class Role extends AuditModel<String> {

	@Column(name = RoleTable.ROLE_NAME)
	private String roleName;

	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(
			name = AppTables.ROLE_PRIVILEGE_NAME,
			joinColumns = @JoinColumn(name = RoleTable.ROLE_ID),
			inverseJoinColumns = @JoinColumn(name = RoleTable.PRIVILEGE_ID)
	)
	private Collection<Privilege> privileges;

	@Column(name = RoleTable.ROLE_TYPE)
	@Enumerated(EnumType.STRING)
	private RoleType roleType;

	@Column(name = RoleTable.DESCRIPTION, columnDefinition = CommonConstants.DEFAULT_LARGE_TEXT_DATA_TYPE)
	private String description;

	@Column(name = RoleTable.IMAGE_URL)
	private String imageUrl;

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Collection<Privilege> getPrivileges() {
		return privileges;
	}

	public void setPrivileges(Collection<Privilege> privileges) {
		this.privileges = privileges;
	}

	public RoleType getRoleType() {
		return roleType;
	}

	public void setRoleType(RoleType roleType) {
		this.roleType = roleType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
