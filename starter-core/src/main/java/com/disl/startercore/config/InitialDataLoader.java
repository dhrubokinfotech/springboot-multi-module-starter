package com.disl.startercore.config;

import com.disl.startercore.constants.AppConstants;
import com.disl.startercore.entities.Privilege;
import com.disl.startercore.entities.Role;
import com.disl.startercore.entities.User;
import com.disl.startercore.enums.RoleType;
import com.disl.startercore.services.PrivilegeService;
import com.disl.startercore.services.RoleService;
import com.disl.startercore.services.UserService;
import com.disl.localization.language.entities.Language;
import com.disl.localization.language.service.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.*;

import static com.disl.commons.constants.CommonConstants.*;

@Component
public class InitialDataLoader implements ApplicationListener<ApplicationContextEvent>{

	private boolean alreadySetup = false;

	@Autowired
	private RoleService roleService;

	@Autowired
	private UserService loginService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private PrivilegeService privilegeService;

	@Autowired
	private LanguageService languageService;

	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		if (!languageService.existsByCode(DEFAULT_LANGUAGE_CODE)) {
			Language language = new Language();
			language.setName(DEFAULT_LANGUAGE_NAME);
			language.setCode(DEFAULT_LANGUAGE_CODE);
			language.setActive(true);
			languageService.save(language);
		}

		if (!languageService.existsByCode(BANGLA_LANGUAGE_CODE)) {
			Language language = new Language();
			language.setName(BANGLA_LANGUAGE_NAME);
			language.setCode(BANGLA_LANGUAGE_CODE);
			language.setActive(true);
			languageService.save(language);
		}

		List<Privilege> superAdminPrivileges = new ArrayList<>();

		for (Map.Entry<String, String> entry : AppConstants.PERMISSIONS.entrySet()) {
			boolean ifPrivilegeExists = this.checkIfPrivilegeExist(entry.getKey());

			if (!ifPrivilegeExists) {
				Privilege newPrivilege = privilegeService.createPrivilege(entry.getKey(),entry.getValue());
				superAdminPrivileges.add(newPrivilege);
			}
		}

		if(checkIfRoleExist(AppConstants.INITIAL_ROLE)) {
			Role superAdminRole = roleService.findRoleByName(AppConstants.INITIAL_ROLE);
			superAdminRole.getPrivileges().addAll(superAdminPrivileges);
			roleService.saveRole(superAdminRole);
		}

		if (alreadySetup || checkIfSuperAdminExist()) {
			return;
		}

		List<Privilege> consumerPrivileges = new ArrayList<>();
		Privilege newPrivilege = privilegeService.createPrivilege(AppConstants.CONSUMER_PERMISSION,AppConstants.CONSUMER_PERMISSION_DESC);
		superAdminPrivileges.add(newPrivilege);
		consumerPrivileges.add(newPrivilege);

		roleService.createRole(AppConstants.USER_ROLE,RoleType.USER,null, consumerPrivileges);
		roleService.createRole(AppConstants.INITIAL_ROLE,RoleType.ADMIN,null, superAdminPrivileges);

		Set<Role> roles = new HashSet<>();
		Role role = roleService.findRoleByName(AppConstants.INITIAL_ROLE);
		if (role != null) {
			roles.add(role);
		}

		User superAdminUser = new User();
		superAdminUser.setRoles(roles);
		superAdminUser.setVerified(true);
		superAdminUser.setBanned(false);
		superAdminUser.setName(AppConstants.INITIAL_ROLE);
		superAdminUser.setEmail(AppConstants.INITIAL_USERNAME);
		superAdminUser.setPassword(passwordEncoder.encode(AppConstants.INITIAL_PASSWORD));
		loginService.saveUser(superAdminUser);

		alreadySetup = true;
	}

	private boolean checkIfPrivilegeExist (String privilegeName) {
		return privilegeService.findByPrivilegeName(privilegeName) != null;
	}

	private boolean checkIfSuperAdminExist () {
		return loginService.findByEmail(AppConstants.INITIAL_USERNAME) != null;
	}

	private boolean checkIfRoleExist (String roleName) {
		return roleService.findRoleByName(roleName) != null;
	}
}
