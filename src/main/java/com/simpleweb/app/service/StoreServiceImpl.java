package com.simpleweb.app.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simpleweb.app.entity.Store;
import com.simpleweb.app.exception.BadRequestException;
import com.simpleweb.app.exception.ResourceNotFoundException;
import com.simpleweb.app.repository.StoreRepository;
import com.simpleweb.app.service.dto.ItemDto;
import com.simpleweb.app.service.dto.StoreDto;
import com.simpleweb.app.service.mapper.StoreMapper;

@Service
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;
	private final StoreMapper storeMapper;
	private final ItemService itemService;

	@Autowired
	public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper, ItemService itemService) {
		this.storeRepository = storeRepository;
		this.storeMapper = storeMapper;
		this.itemService = itemService;
	}

	@Override
	public List<StoreDto> findAll() {
		List<StoreDto> storesDto = StreamSupport
				.stream(storeMapper.toStoresDto(storeRepository.findAll()).spliterator(), false)
				.collect(Collectors.toList());
		return storesDto;
	}

	@Override
	public StoreDto findByCod(String cod) {
		Store store = storeRepository.findByCod(cod).orElseThrow(() -> new ResourceNotFoundException("Store not found: " + cod));
		return storeMapper.toStoreDto(store);
	}

	@Override
	public StoreDto addItem(String storeCod, String itemCod) {
		StoreDto storeDto = findByCod(storeCod);
		ItemDto itemDto = itemService.findByCod(itemCod);

		if (!storeDto.getItems().add(itemDto)) {
			throw new BadRequestException("Item already exists on Store");
		}

		storeDto.getItems().add(itemDto);
		Store store = storeRepository.save(storeMapper.toStore(storeDto));
		return storeMapper.toStoreDto(store);
	}

}
