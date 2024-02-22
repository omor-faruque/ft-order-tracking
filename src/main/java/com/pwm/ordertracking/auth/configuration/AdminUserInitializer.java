package com.pwm.ordertracking.auth.configuration;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.pwm.ordertracking.auth.model.ERole;
import com.pwm.ordertracking.auth.model.Role;
import com.pwm.ordertracking.auth.model.User;
import com.pwm.ordertracking.auth.repository.RoleRepository;
import com.pwm.ordertracking.auth.repository.UserRepository;

@Configuration
public class AdminUserInitializer {
	private final UserRepository userRepository;
	private final RoleRepository roleRepository;
	private final PasswordEncoder passwordEncoder;
	
	@Value("${admin.username}")
	private String username;
	@Value("${admin.email}")
	private String email;
	@Value("${admin.password}")
	private String password;

	public AdminUserInitializer(UserRepository userRepository, RoleRepository roleRepository,
			PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
		this.passwordEncoder = passwordEncoder;
	}

	@Bean
	public CommandLineRunner initializeAdminUser() {
		
		return args -> {
			Optional<User> userOptional = userRepository.findByUsername(username);
			if (!userOptional.isPresent()) {
				System.out.println("***** Admin User being initialized *****");
				User adminUser = new User();
				adminUser.setUsername(username);
				adminUser.setEmail(email);
				adminUser.setPassword(passwordEncoder.encode(password));
				
				Set<Role> roles = new HashSet<Role>();
				roles.add(this.roleRepository.findByName(ERole.ROLE_ADMIN).get());
				roles.add(this.roleRepository.findByName(ERole.ROLE_USER).get());
				adminUser.setRoles(roles);
				
				this.userRepository.save(adminUser);
				System.out.println("***** Admin User created successfully. *****");

			}
			else {
	            System.out.println("***** Admin User already exists. *****");
	        }
		};
	}

	
}

