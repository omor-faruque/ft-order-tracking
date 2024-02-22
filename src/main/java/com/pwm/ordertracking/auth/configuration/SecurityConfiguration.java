package com.pwm.ordertracking.auth.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.pwm.ordertracking.auth.jwt.AuthTokenFilter;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

	private UserDetailsService userDetailsService;
	private AuthenticationEntryPoint unAuthorizedHandler;
	private AuthTokenFilter authTokenFilter;

	@Autowired
	public SecurityConfiguration(UserDetailsService userDetailsService, AuthenticationEntryPoint unAuthorizedHandler,
			AuthTokenFilter authTokenFilter) {
		this.userDetailsService = userDetailsService;
		this.unAuthorizedHandler = unAuthorizedHandler;
		this.authTokenFilter = authTokenFilter;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setPasswordEncoder(passwordEncoder());
		authProvider.setUserDetailsService(userDetailsService);
		return authProvider;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf((csrf) -> csrf.disable())
				.exceptionHandling((exception) -> exception.authenticationEntryPoint(unAuthorizedHandler))
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class).build();
	}


}
