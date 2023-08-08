package com.disl.startercore.repository;

import com.disl.startercore.entities.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege,Long> {

	Optional<Privilege> findByNameEquals(String name);
	List<Privilege> findAllByNameIn(List<String> names);
}
