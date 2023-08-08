package com.disl.startercore.constants;

import com.disl.startercore.entities.Role;
import com.disl.startercore.entities.User;
import com.disl.startercore.enums.RoleType;
import com.disl.startercore.security.CustomUserDetails;
import com.disl.startercore.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

public final class AppUtils {

	private AppUtils() {}

	public static User getLoggedInUser(UserService userService, RoleType roleType) {
		User user = getLoggedInUser(userService);
		Optional<Role> optionalRole = user.getRoles()
				.stream()
				.filter(role -> role.getRoleType().equals(roleType))
				.findAny();

		return optionalRole.isEmpty() ? null : user;
	}

	public static User getLoggedInUser(UserService userService) {
		User user;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if(authentication == null) return null;

		try {
			Object principal = authentication.getPrincipal();

			if (principal instanceof CustomUserDetails) {
				CustomUserDetails customUserDetails = (CustomUserDetails) principal;

				user = new User();
				user.setId(customUserDetails.getId());
				user.setName(customUserDetails.getName());
				user.setEmail(customUserDetails.getEmail());
				user.setRoles(customUserDetails.getRoles());
				user.setVerified(customUserDetails.isVerified());
				user.setPassword(customUserDetails.getPassword());
			} else {
				user = userService.findByEmail(authentication.getName());
			}
		} catch(Exception e) {
			user = userService.findByEmail(authentication.getName());
		}

		return user;
	}
}
