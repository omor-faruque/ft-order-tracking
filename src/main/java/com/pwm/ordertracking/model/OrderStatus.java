package com.pwm.ordertracking.model;

public enum OrderStatus {
	PENDING("Pending"), 
	PROCESSING("Processing"), 
	SHIPPED("Shipped"), 
	OUT_FOR_DELIVERY("Out For Delivery"),
	DELIVERED("Delivered"), 
	CANCELLED("Cancelled");

	private final String statusName;

	OrderStatus(String statusName) {
		this.statusName = statusName;
	}

	public String getStatusName() {
		return statusName;
	}
}
