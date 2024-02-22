package com.pwm.ordertracking.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwm.ordertracking.auth.jwt.JwtUtils;
import com.pwm.ordertracking.auth.jwt.TokenBlacklist;
import com.pwm.ordertracking.auth.payloads.ChangePasswordRequest;
import com.pwm.ordertracking.auth.payloads.MessageResponse;
import com.pwm.ordertracking.auth.payloads.SigninRequest;
import com.pwm.ordertracking.auth.payloads.SigninResponse;
import com.pwm.ordertracking.auth.repository.UserRepository;
import com.pwm.ordertracking.auth.services.AuthService;
import com.pwm.ordertracking.auth.services.UserDetailsImpl;
import com.pwm.ordertracking.exception.ErrorItem;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = { "http://localhost:4200", "http://localhost:4201" })
public class AuthController {

	private final AuthService authService;
	private final AuthenticationManager authenticationManager;
	private final JwtUtils jwtUtils;
	private final TokenBlacklist tokenBlacklist;

	@Autowired
	public AuthController(AuthService authService, UserRepository userRepository,
			AuthenticationManager authenticationManager, JwtUtils jwtUtils, TokenBlacklist tokenBlacklist) {
		this.authService = authService;
		this.authenticationManager = authenticationManager;
		this.jwtUtils = jwtUtils;
		this.tokenBlacklist = tokenBlacklist;
	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody SigninRequest signinRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(signinRequest.getEmail(), signinRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = this.jwtUtils.generateJwtToken(authentication);
			
			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

			return ResponseEntity.ok(
					new SigninResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail()));

		} catch (Exception e) {
			ErrorItem errorItem = new ErrorItem(e.getMessage() + ". Error occured for: " + signinRequest.getEmail(), HttpStatus.BAD_REQUEST);
			return new ResponseEntity<>(errorItem, HttpStatus.BAD_REQUEST);
		}
	}
	
	@PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordRequest request) {
		
		// Get authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Get UserDetails from authentication
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        
        // Get email
        String email = userDetails.getEmail();
		

        boolean success = authService.changePassword(email, request.getCurrentPassword(), request.getNewPassword());
        if (success) {
            return ResponseEntity.ok("Password changed successfully");
        } else {
            return ResponseEntity.badRequest().body("Failed to change password");
        }
    }
	
	
	@PostMapping("/signout")
	public ResponseEntity<MessageResponse> signout(@RequestHeader("Authorization") String token) {
		String jwtToken = extractToken(token);
		
		tokenBlacklist.addToBlacklist(jwtToken);
		
		return ResponseEntity.ok(new MessageResponse("Logout successful"));
	}
	
	private String extractToken(String authorizationHeader) {
        return authorizationHeader.replace("Bearer ", "");
    }

	
	

}
