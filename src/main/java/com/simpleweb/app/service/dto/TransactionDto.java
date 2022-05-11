package com.simpleweb.app.service.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionDto {
	private Long id;
	private ClientDto client;
	private StoreDto store;
	private ItemDto item;
	private int amount;
	private Date date;
}
