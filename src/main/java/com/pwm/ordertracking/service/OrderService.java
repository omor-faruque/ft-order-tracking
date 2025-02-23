package com.pwm.ordertracking.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pwm.ordertracking.dto.OrderStatusDTO;
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
	public OrderStatusDTO getStatusByTrackingId(String trackingId) {
		Order order = orderRepository.findByTrackingId(trackingId);
		
        if (order != null) {
            return new OrderStatusDTO(order.getOrderStatus().name(), order.getOrderStatus().getDisplayName()) ;
        } else {
            throw new NoSuchElementException("Order with tracking ID " + trackingId + " not found");
        }
	}
	public List<Order> getOrdersWithStatus(OrderStatus status) {
		return orderRepository.findByOrderStatus(status);
	}

	public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
		Optional<Order> optionalOrder = orderRepository.findById(orderId);

		if (optionalOrder.isPresent()) {
			Order order = optionalOrder.get();
			order.setOrderStatus(newStatus);
			return orderRepository.save(order);
		} else {
			throw new NoSuchElementException("Order not found with ID: " + orderId);
		}
	}

	public Order createOrder(Order order) {
		Order orderEntity = new Order(order.getCustomerName(), order.getSourceOrderId());
		
	    if (order.getShippingAddress() != null) {
	        orderEntity.setShippingAddress(order.getShippingAddress());
	    }

	    if (order.getNote() != null) {
	        orderEntity.setNote(order.getNote());
	    }

	    if (isValidOrderStatus(order.getOrderStatus())) {
	        orderEntity.setOrderStatus(order.getOrderStatus());
	    } else {
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
