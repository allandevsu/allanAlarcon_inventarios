package com.simpleweb.app.service.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class OrdersDto {
	private Set<OrderDto> orders = new HashSet<>();
}
