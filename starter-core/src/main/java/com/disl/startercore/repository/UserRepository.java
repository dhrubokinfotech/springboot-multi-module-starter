package com.disl.startercore.repository;

import com.disl.startercore.entities.Role;
import com.disl.startercore.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
	
	List<User> findByRoles(Role role);
	List<User> findByRolesIn(Role[] roles);
	Optional<User> findTopByEmail(String email);
	Optional<User> findByPasswordResetToken (String token);

}
