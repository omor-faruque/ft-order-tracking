package com.pwm.ordertracking.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwm.ordertracking.model.Order;
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
	
	public Order createOrder(Order order) {
		Order orderEntity = new Order();
		orderEntity.setCustomerName(order.getCustomerName());
		orderEntity.setSourceOrderId(order.getSourceOrderId());
		orderEntity.setShippingAddress(order.getShippingAddress());
		orderEntity.setOrderStatus(order.getOrderStatus());
		return orderRepository.save(orderEntity);
	}
	
	public void deleteOrderById(Long id) {
		this.orderRepository.deleteById(id);
	}
	

}
