package com.pwm.ordertracking.auth.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pwm.ordertracking.auth.model.ERole;
import com.pwm.ordertracking.auth.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);

	Optional<User> findByEmail(String email);
	
	List<User> findByRolesName(ERole name);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
	
}
