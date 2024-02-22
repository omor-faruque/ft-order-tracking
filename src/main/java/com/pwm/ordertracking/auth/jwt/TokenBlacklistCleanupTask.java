package com.pwm.ordertracking.auth.jwt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TokenBlacklistCleanupTask {
	private final int cleanupTimeInMinutes = 60;
	private static final Logger logger = LoggerFactory.getLogger(TokenBlacklistCleanupTask.class);
	private final TokenBlacklist tokenBlacklist;

	public TokenBlacklistCleanupTask(TokenBlacklist tokenBlacklist) {
		this.tokenBlacklist = tokenBlacklist;
	}

	// Schedule the cleanup task to run every hour (every 60 minutes)
	@Scheduled(fixedRate = cleanupTimeInMinutes * 60 * 1000) // // 60 minutes = 60 * 60 * 1000 milliseconds
	public void cleanupExpiredTokens() {
		logger.info("#### -----Cleanup Expired Tokens START----- ####");
		logger.info("No of token in the memory: " + tokenBlacklist.noOfBlacklistedToken());
		tokenBlacklist.cleanupExpiredTokens();
		logger.info("#### -----Cleanup Expired Tokens END----- ####");
	}

}