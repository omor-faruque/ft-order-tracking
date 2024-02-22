package com.pwm.ordertracking.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.pwm.ordertracking.auth.model.ERole;
import com.pwm.ordertracking.auth.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
