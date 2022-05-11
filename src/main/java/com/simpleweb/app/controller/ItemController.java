package com.simpleweb.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simpleweb.app.exception.BadRequestException;
import com.simpleweb.app.service.ItemService;
import com.simpleweb.app.service.dto.ItemDto;

@RestController
@RequestMapping("api/items")
public class ItemController {

	private final ItemService itemService;

	@Autowired
	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ItemDto> list(){
		return itemService.findAll();
	}

	@RequestMapping(method=RequestMethod.PATCH, value="/{cod}/stock")
	@ResponseStatus(HttpStatus.OK)
	public ItemDto updateStock(@RequestBody ItemDto itemDto, @PathVariable String cod){
		if (itemDto.getStock() <= 0) {
			throw(new BadRequestException("Stock cannot be less or equal than 0"));
		}
		return itemService.updateStock(cod, itemDto);
	}
}
