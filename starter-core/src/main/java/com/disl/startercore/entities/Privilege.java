package com.disl.startercore.entities;

import com.disl.commons.models.AuditModel;
import com.disl.startercore.constants.AppTables;
import com.disl.startercore.constants.AppTables.PrivilegeTable;
import jakarta.persistence.*;

@Entity
@Table(name = AppTables.PRIVILEGE_NAME)
public class Privilege extends AuditModel<String> {

	@Column(name = PrivilegeTable.NAME)
	private String name;
	
	@Column(name = PrivilegeTable.DESC_NAME)
	private String descName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescName() {
		return descName;
	}

	public void setDescName(String descName) {
		this.descName = descName;
	}
}
