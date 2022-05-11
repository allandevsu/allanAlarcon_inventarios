package com.simpleweb.app.service;


import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simpleweb.app.entity.Item;
import com.simpleweb.app.exception.ResourceNotFoundException;
import com.simpleweb.app.repository.ItemRepository;
import com.simpleweb.app.service.dto.ItemDto;
import com.simpleweb.app.service.mapper.ItemMapper;

@Service
public class ItemServiceImpl implements ItemService {

	private final ItemRepository itemRepository;
	private final ItemMapper itemMapper;

	@Autowired
	public ItemServiceImpl(ItemRepository itemRepository, ItemMapper itemMapper) {
		this.itemRepository = itemRepository;
		this.itemMapper = itemMapper;
	}

	@Override
	public List<ItemDto> findAll() {
		List<ItemDto> itemsDto = StreamSupport
				.stream(itemMapper.toItemsDto(itemRepository.findAll()).spliterator(), false)
				.collect(Collectors.toList());
		return itemsDto;
	}

	@Override
	public ItemDto updateStock(String cod, ItemDto itemDto) {
		Item item = itemRepository.findByCod(cod).orElseThrow(() -> new ResourceNotFoundException("Item not found: " + cod));
		item.setStock(itemDto.getStock());
		itemRepository.save(item);
		return itemMapper.toItemDto(item);
	}

	@Override
	public ItemDto findByCod(String cod) {
		Item item = itemRepository.findByCod(cod).orElseThrow(() -> new ResourceNotFoundException("Item not found: " + cod));
		return itemMapper.toItemDto(item);
	}

}
