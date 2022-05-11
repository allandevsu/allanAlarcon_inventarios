package com.simpleweb.app.client;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.simpleweb.app.client.dto.ItemList;
import com.simpleweb.app.client.dto.ItemStock;
import com.simpleweb.app.entity.Item;

@Service
public class MockServiceImpl implements MockService {

	private final RestTemplate restTemplate;

	@Autowired
    public MockServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

	@Override
	public Iterable<Item> getItems() {
		ResponseEntity<ItemList> rateResponse =
				restTemplate.exchange("https://mocki.io/v1/67b65a7c-716f-41f8-a4f7-878cc8e740f8",
						HttpMethod.GET, null, new ParameterizedTypeReference<ItemList>() {});
		ItemList items = rateResponse.getBody();
		return items.getProds();
	}

	@Override
	public ItemStock getTenStock() {
		ResponseEntity<ItemStock> rateResponse =
				restTemplate.exchange("https://mocki.io/v1/8f45ce6a-762f-42e8-b071-d97992c36f0b",
						HttpMethod.GET, null, new ParameterizedTypeReference<ItemStock>() {});
		return rateResponse.getBody();
	}

	@Override
	@Async
	public Future<ItemStock> getFiveStock() {
		ResponseEntity<ItemStock> rateResponse =
				restTemplate.exchange("https://mocki.io/v1/7d624fa5-e8ef-47ad-9d49-3a303ec994be",
						HttpMethod.GET, null, new ParameterizedTypeReference<ItemStock>() {});
		return new AsyncResult<ItemStock>(rateResponse.getBody());
	}
}
