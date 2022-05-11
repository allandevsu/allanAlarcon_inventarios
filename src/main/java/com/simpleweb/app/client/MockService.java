package com.simpleweb.app.client;

import java.util.concurrent.Future;

import com.simpleweb.app.client.dto.ItemStock;
import com.simpleweb.app.entity.Item;

public interface MockService {

	public Iterable<Item> getItems();
	public ItemStock getTenStock();
	public Future<ItemStock> getFiveStock();
}
