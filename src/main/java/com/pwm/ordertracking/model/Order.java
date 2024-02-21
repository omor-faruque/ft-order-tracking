package com.pwm.ordertracking.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "orders")
public class Order {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 100)
	@Column(name = "custome_name")
	private String customerName;

	@Size(max = 100)
	@Column(name = "shipping_address")
	private String shippingAddress;

	@NotBlank
	@Size(max = 100)
	@Column(name = "sourde_order_id")
	private String sourceOrderId;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus orderStatus;

	@Column(name = "tracking_id")
	private String trackingId;
	
	public Order() {}

	public Order(String customerName, String sourceOrderId) {
		this.customerName = customerName;
		this.sourceOrderId = sourceOrderId;
		generateTrackingId();
	}
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
		
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public String getSourceOrderId() {
		return sourceOrderId;
	}

	public void setSourceOrderId(String sourceOrderId) {
		this.sourceOrderId = sourceOrderId;
		generateTrackingId();
	}

	public OrderStatus getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}

	public Long getId() {
		return id;
	}

	public String getTrackingId() {
		return trackingId;
	}

	private void generateTrackingId() {
		this.trackingId ="TR"+this.sourceOrderId;
	}
	


}
