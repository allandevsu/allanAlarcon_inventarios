package com.simpleweb.app.service.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.simpleweb.app.entity.Item;
import com.simpleweb.app.service.dto.ItemDto;

@Component
public class ItemMapper {

	public Iterable<ItemDto> toItemsDto(Iterable<Item> items){
		Iterable<ItemDto> itemsDto = new ArrayList<>();
		for (Item item: items) {
			((ArrayList<ItemDto>) itemsDto).add(toItemDto(item));
		}
		return itemsDto;
	}

	public ItemDto toItemDto(Item item){
		return ItemDto
				.builder()
				.id(item.getId())
				.cod(item.getCod())
				.name(item.getName())
				.price(item.getPrice())
				.stock(item.getStock())
				.build();
	}

	public Item toItem(ItemDto itemDto){
		return Item
				.builder()
				.id(itemDto.getId())
				.cod(itemDto.getCod())
				.name(itemDto.getName())
				.price(itemDto.getPrice())
				.stock(itemDto.getStock())
				.build();
	}
}
