package com.pwm.ordertracking.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pwm.ordertracking.dto.OrderStatusDTO;
import com.pwm.ordertracking.model.OrderStatus;

@RestController
@RequestMapping("/api/order-statuses")
public class OrderStatusController {

	@GetMapping
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
}
