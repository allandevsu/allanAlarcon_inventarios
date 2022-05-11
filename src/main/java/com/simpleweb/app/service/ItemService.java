package com.simpleweb.app.service;

import java.util.List;

import com.simpleweb.app.service.dto.ItemDto;

public interface ItemService {

	public List<ItemDto> findAll();
	public ItemDto updateStock(String cod, ItemDto item);
	public ItemDto findByCod(String cod);
}
