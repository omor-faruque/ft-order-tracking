package com.pwm.ordertracking.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pwm.ordertracking.model.Order;
import com.pwm.ordertracking.model.OrderStatus;

public interface OrderRepository extends JpaRepository<Order, Long> {
	List<Order> findByOrderStatus(OrderStatus status);
}
