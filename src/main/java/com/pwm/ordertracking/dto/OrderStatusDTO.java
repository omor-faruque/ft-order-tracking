package com.pwm.ordertracking.dto;

public class OrderStatusDTO {
	private String name;
    private String displayName;
    
    public OrderStatusDTO() {}
    
    public OrderStatusDTO(String name, String displayName) {
    	this.name = name;
    	this.displayName = displayName;
    }
    
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
    
}
