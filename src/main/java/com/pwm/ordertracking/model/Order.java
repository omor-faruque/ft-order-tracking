package com.pwm.ordertracking.model;

import java.util.UUID;
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

	@Size(max = 100)
	@Column(name = "amazon_order_id")
	private String amazonOrderId;

	@Enumerated(EnumType.STRING)
	@Column(name = "order_status")
	private OrderStatus orderStatus;

	@Column(name = "tracking_id")
	private String trackingId;

	public Order() {
		generateTrackingId();
	}

	public Order(String customerName, String shippingAddress, String amazonOrderId, OrderStatus orderStatus) {
		this.customerName = customerName;
		this.shippingAddress = shippingAddress;
		this.amazonOrderId = amazonOrderId;
		this.orderStatus = orderStatus;
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

	public String getAmazonOrderId() {
		return amazonOrderId;
	}

	public void setAmazonOrderId(String amazonOrderId) {
		this.amazonOrderId = amazonOrderId;
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

	public void setTrackingId(String trackingId) {
		this.trackingId = trackingId;
	}

	private void generateTrackingId() {

		this.trackingId = UUID.randomUUID().toString();
	}
	


}
