package com.simpleweb.app.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.simpleweb.app.client.MockService;
import com.simpleweb.app.entity.Item;
import com.simpleweb.app.entity.Store;
import com.simpleweb.app.repository.ItemRepository;
import com.simpleweb.app.repository.StoreRepository;

@Component
public class InitialData implements CommandLineRunner {

	private final MockService mockService;
	private final ItemRepository itemRepository;
	private final StoreRepository storeRepository;

	@Autowired
	public InitialData(MockService mockService, ItemRepository itemRepository, StoreRepository storeRepository) {
		this.mockService = mockService;
		this.itemRepository = itemRepository;
		this.storeRepository = storeRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		Iterable<Item> items = mockService.getItems();
		itemRepository.saveAll(items);

		Store store1 = new Store();
		store1.setCod("store-1");
		store1.setName("store-name-1");
		storeRepository.save(store1);

		Store store2 = new Store();
		store2.setCod("store-2");
		store2.setName("store-name-2");
		storeRepository.save(store2);

		Store store3 = new Store();
		store3.setCod("store-3");
		store3.setName("store-name-3");
		storeRepository.save(store3);

		Store store4 = new Store();
		store4.setCod("store-4");
		store4.setName("store-name-4");
		storeRepository.save(store4);
	}

}
