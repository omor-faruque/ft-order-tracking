package com.pwm.ordertracking.auth.payloads;

public class ChangePasswordRequest {
	private String currentPassword;
    private String newPassword;

    // Default constructor (required for JSON deserialization)
    public ChangePasswordRequest() {}

    // Getters and setters
    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
