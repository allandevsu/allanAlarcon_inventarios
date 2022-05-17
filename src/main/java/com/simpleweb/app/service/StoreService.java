package com.simpleweb.app.service;

import java.util.List;

import com.simpleweb.app.service.dto.StoreDto;

public interface StoreService {

	public List<StoreDto> findAll();
	public StoreDto findByCod(String cod);
	public StoreDto addItem(String storeCod, String itemCod);
}
