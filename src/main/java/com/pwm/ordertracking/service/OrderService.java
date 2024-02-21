package com.pwm.ordertracking.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwm.ordertracking.model.Order;
import com.pwm.ordertracking.model.OrderStatus;
import com.pwm.ordertracking.repository.OrderRepository;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

	@Autowired
	public OrderService(OrderRepository orderRepository) {
		this.orderRepository = orderRepository;
	}

	public List<Order> getOrders() {
		return orderRepository.findAll();
	}
	
	public List<Order> getOrdersWithStatus(OrderStatus status) {
		return orderRepository.findByOrderStatus(status);
	}
	


	public Order createOrder(Order order) {
		
		Order orderEntity = new Order(order.getCustomerName(),order.getSourceOrderId());
		orderEntity.setShippingAddress(order.getShippingAddress());
		if (isValidOrderStatus(order.getOrderStatus())) {
			orderEntity.setOrderStatus(order.getOrderStatus());
		}
		else {
			orderEntity.setOrderStatus(OrderStatus.PROCESSING);
		}
		
		return orderRepository.save(orderEntity);
	}

	public void deleteOrderById(Long id) {
		this.orderRepository.deleteById(id);
	}

	private boolean isValidOrderStatus(OrderStatus status) {
		for (OrderStatus os : OrderStatus.values()) {
			if (os.equals(status)) {
				return true;
			}
		}
		return false;
	}

}
