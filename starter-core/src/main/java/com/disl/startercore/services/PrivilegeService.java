package com.disl.startercore.services;

import com.disl.startercore.entities.Privilege;
import com.disl.startercore.repository.PrivilegeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PrivilegeService {

	@Autowired
    private PrivilegeRepository privilegeRepository;
	
	public Privilege findByPrivilegeName (String name) {
		return privilegeRepository.findByNameEquals(name).orElse(null);
	}
	
	public Privilege createPrivilege(String name, String descName) {
		Privilege privilege = new Privilege();
		privilege.setName(name);
		privilege.setDescName(descName);
		return privilegeRepository.save(privilege);
	}
	
	public List<Privilege> viewAllPrivileges() {
		return privilegeRepository.findAll();
	}
	
	public Privilege findByPrivilegeId (long id) {
		return privilegeRepository.findById(id).orElse(null);
	}
}
