package com.pwm.ordertracking.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.pwm.ordertracking.dto.OrderStatusDTO;
import com.pwm.ordertracking.model.Order;
import com.pwm.ordertracking.model.OrderStatus;
import com.pwm.ordertracking.service.OrderService;

@RestController
@RequestMapping("/api")
public class OrderController {

	private final OrderService orderService;

	@Autowired
	public OrderController(OrderService orderService) {
		this.orderService = orderService;
	}

	@GetMapping("/orders")
	public ResponseEntity<List<Order>> getAllOrders() {
		List<Order> orders = orderService.getOrders();
		if (orders == null || orders.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<List<Order>>(orders, HttpStatus.OK);
	}

	@GetMapping("/orders/statuses")
	public List<OrderStatusDTO> getAllOrderStatuses() {

		List<OrderStatusDTO> orderStatusDTOList = new ArrayList<>();

		for (OrderStatus status : OrderStatus.values()) {

			OrderStatusDTO orderStatusDTO = new OrderStatusDTO();

			orderStatusDTO.setName(status.name());
			orderStatusDTO.setDisplayName(status.getDisplayName());
			orderStatusDTOList.add(orderStatusDTO);
		}
		return orderStatusDTOList;
	}

	@GetMapping("/orders/status")
	public ResponseEntity<OrderStatusDTO> getStatusByTrackingId(@RequestParam String trackingId) {
		OrderStatusDTO orderStatusDto = orderService.getStatusByTrackingId(trackingId);
		return ResponseEntity.ok(orderStatusDto);
	}

	@GetMapping("/orders/{status}")
	public ResponseEntity<List<Order>> getAllOrders(@PathVariable String status) {
		try {
			OrderStatus orderStatus = OrderStatus.valueOf(status.toUpperCase());
			List<Order> orders = orderService.getOrdersWithStatus(orderStatus);

			if (orders.isEmpty()) {
				return ResponseEntity.noContent().build();
			} else {
				return ResponseEntity.ok(orders);
			}
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid order status");
		}
	}

	@PostMapping("/orders")
	public ResponseEntity<Order> addOrder(@RequestBody Order order) {
		return new ResponseEntity<Order>(orderService.createOrder(order), HttpStatus.CREATED);
	}

	@PutMapping("/orders/{orderId}")
	public ResponseEntity<Order> updateOrderStatus(@PathVariable Long orderId, @RequestParam String status) {

		try {
			OrderStatus newStatus = OrderStatus.valueOf(status.toUpperCase());
			Order updatedOrder = orderService.updateOrderStatus(orderId, newStatus);
			return ResponseEntity.ok(updatedOrder);
		} catch (IllegalArgumentException e) {
			throw new IllegalArgumentException("Invalid order status");
		} catch (NoSuchElementException e) {
			throw new NoSuchElementException("No such order exists");
		}
	}

	@DeleteMapping("/orders/{id}")
	public ResponseEntity<HttpStatus> deleteOrderById(@PathVariable Long id) {
		this.orderService.deleteOrderById(id);
		return new ResponseEntity<HttpStatus>(HttpStatus.NO_CONTENT);

	}

}
