package com.simpleweb.app.service.dto;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class OrderDto {
	public String storeCod;
	private Set<ItemOrderDto> items = new HashSet<>();
}
