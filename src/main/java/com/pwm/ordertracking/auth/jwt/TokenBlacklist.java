package com.pwm.ordertracking.auth.jwt;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class TokenBlacklist {
	private static final Logger logger = LoggerFactory.getLogger(TokenBlacklist.class);
	private final Set<String> blacklistedTokens = new HashSet<String>();

	private JwtUtils jwtUtils;

	@Autowired
	public TokenBlacklist(JwtUtils jwtUtils) {
		this.jwtUtils = jwtUtils;
	}

	// Add a token to the blacklist
	public void addToBlacklist(String token) {
		blacklistedTokens.add(token);
	}

	// Check if a token is in the blacklist
	public boolean isTokenBlacklisted(String token) {
		return blacklistedTokens.contains(token);
	}

	// Cleanup method to remove expired tokens
	public void cleanupExpiredTokens() {
		Iterator<String> iterator = blacklistedTokens.iterator();

		while (iterator.hasNext()) {
			String token = iterator.next();
			try {
				jwtUtils.extractExpirationTimeFromToken(token);
			} catch (ExpiredJwtException e) {
				iterator.remove();
				logger.info("Token removed: " + token);
			} catch (Exception e) {
				logger.error("Error during token cleanup: " + e.getMessage());
			}
		}
	}

	public int noOfBlacklistedToken() {
		return blacklistedTokens.size();
	}
}
