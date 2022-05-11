package com.simpleweb.app.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simpleweb.app.entity.Store;
import com.simpleweb.app.exception.ResourceNotFoundException;
import com.simpleweb.app.repository.StoreRepository;
import com.simpleweb.app.service.dto.StoreDto;
import com.simpleweb.app.service.mapper.StoreMapper;

@Service
public class StoreServiceImpl implements StoreService {

	private final StoreRepository storeRepository;
	private final StoreMapper storeMapper;

	@Autowired
	public StoreServiceImpl(StoreRepository storeRepository, StoreMapper storeMapper) {
		this.storeRepository = storeRepository;
		this.storeMapper = storeMapper;
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

}
