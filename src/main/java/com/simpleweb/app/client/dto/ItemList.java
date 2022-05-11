package com.simpleweb.app.client.dto;

import com.simpleweb.app.entity.Item;

import lombok.Getter;

@Getter
public class ItemList {
	Iterable<Item> prods;
}
