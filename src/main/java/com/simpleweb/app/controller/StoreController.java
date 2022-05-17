package com.simpleweb.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simpleweb.app.service.StoreService;
import com.simpleweb.app.service.dto.StoreDto;

@RestController
@RequestMapping("api/stores")
public class StoreController {

	private final StoreService storeService;

	@Autowired
	public StoreController(StoreService storeService) {
		this.storeService = storeService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<StoreDto> list(){
		return storeService.findAll();
	}

	@RequestMapping(method=RequestMethod.PATCH, value="/{storeCod}/item/{itemCod}")
	@ResponseStatus(HttpStatus.OK)
	public StoreDto addItem(@PathVariable String storeCod, @PathVariable String itemCod){
		return storeService.addItem(storeCod, itemCod);
	}
}
