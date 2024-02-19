package com.pwm.ordertracking.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pwm.ordertracking.model.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
