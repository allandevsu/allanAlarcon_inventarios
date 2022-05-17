package com.simpleweb.app.service.mapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpleweb.app.entity.Item;
import com.simpleweb.app.entity.Store;
import com.simpleweb.app.service.dto.ItemDto;
import com.simpleweb.app.service.dto.StoreDto;

@Component
public class StoreMapper {

	private final ItemMapper itemMapper;

	@Autowired
	public StoreMapper(ItemMapper itemMapper) {
		this.itemMapper = itemMapper;
	}

	public Iterable<StoreDto> toStoresDto(Iterable<Store> stores){
		Iterable<StoreDto> storesDto = new ArrayList<>();
		for (Store store : stores) {
			((ArrayList<StoreDto>) storesDto).add(toStoreDto(store));
		}
		return storesDto;
	}

	public StoreDto toStoreDto(Store store){
		return StoreDto
				.builder()
				.id(store.getId())
				.cod(store.getCod())
				.name(store.getName())
				.items(toSetItemsDto(store.getItems()))
				.build();
	}

	public Store toStore(StoreDto storeDto){
		return Store
				.builder()
				.id(storeDto.getId())
				.cod(storeDto.getCod())
				.name(storeDto.getName())
				.items(toSetItems(storeDto.getItems()))
				.build();
	}

	public Set<ItemDto> toSetItemsDto(Set<Item> items) {
		Set<ItemDto> itemsDto = new HashSet<>();
		for (Item item: items) {
			itemsDto.add(itemMapper.toItemDto(item));
		}
		return itemsDto;
	}

	public Set<Item> toSetItems(Set<ItemDto> itemsDto) {
		Set<Item> items = new HashSet<>();
		for (ItemDto itemDto: itemsDto) {
			items.add(itemMapper.toItem(itemDto));
		}
		return items;
	}
}
