package com.simpleweb.app.service.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.simpleweb.app.entity.Store;
import com.simpleweb.app.service.dto.StoreDto;

@Component
public class StoreMapper {

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
				.build();
	}

	public Store toStore(StoreDto storeDto){
		return Store
				.builder()
				.id(storeDto.getId())
				.cod(storeDto.getCod())
				.name(storeDto.getName())
				.build();
	}
}
