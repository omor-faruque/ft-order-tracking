package com.pwm.ordertracking.model;

public enum OrderStatus {
	PENDING("Pending"), 
	PROCESSING("Processing"), 
	SHIPPED("Shipped"), 
	OUT_FOR_DELIVERY("Out For Delivery"),
	DELIVERED("Delivered"), 
	CANCELLED("Cancelled");

	private final String displayName;

	OrderStatus(String displayName) {
		this.displayName = displayName;
	}

	public String getDisplayName() {
		return displayName;
	}
}
