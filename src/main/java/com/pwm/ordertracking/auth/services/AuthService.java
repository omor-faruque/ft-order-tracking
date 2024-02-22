package com.pwm.ordertracking.auth.services;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pwm.ordertracking.auth.exception.UserAlreadyExistsException;
import com.pwm.ordertracking.auth.model.ERole;
import com.pwm.ordertracking.auth.model.Role;
import com.pwm.ordertracking.auth.model.User;
import com.pwm.ordertracking.auth.repository.RoleRepository;
import com.pwm.ordertracking.auth.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthService {
	private UserRepository userRepository;
	private RoleRepository roleRepository;
	private PasswordEncoder encoder;

	@Autowired
	public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.encoder = encoder;
	}

	public void createUser(User user) {
		checkIfUserExist(user.getEmail());
		Set<Role> roles = getUserRoles(ERole.ROLE_USER);
		User newUser = new User(user.getUsername(), user.getEmail(), encoder.encode(user.getPassword()), roles);
		this.userRepository.save(newUser);
	}
	
	public void promoteUserToAdmin(Long id) {
		Optional<User> existingUser = userRepository.findById(id);
		if (!existingUser.isPresent()) {
	        throw new NoSuchElementException("No user with ID: " + id + " exists");
	    }
		
		existingUser.get().getRoles().add(new Role(ERole.ROLE_ADMIN));
	}

	public User createAdminUser(User user) {
		checkIfUserExist(user.getEmail());
		Set<Role> roles = getUserRoles(ERole.ROLE_ADMIN);
		user.setRoles(roles);
		return this.userRepository.save(user);
	}
	

	private void checkIfUserExist(String email) {
		if (userRepository.existsByEmail(email)) {
			throw new UserAlreadyExistsException("Email '" + email + "' already exists");
		}
	}

	private Set<Role> getUserRoles(ERole role) {
		Set<Role> roles = new HashSet<Role>();

		Role adminRole = this.roleRepository.findByName(ERole.ROLE_ADMIN).get();
		Role userRole = this.roleRepository.findByName(ERole.ROLE_USER).get();

		if (role == ERole.ROLE_ADMIN && adminRole != null) {
			roles.add(adminRole);
		}

		if (userRole != null) {
			roles.add(userRole);
		}
		return roles;
	}
}
