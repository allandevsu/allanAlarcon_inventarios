package com.simpleweb.app.service.mapper;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.simpleweb.app.entity.Transaction;
import com.simpleweb.app.service.dto.TransactionDto;

@Component
public class TransactionMapper {

	private final ClientMapper clientMapper;
	private final StoreMapper storeMapper;
	private final ItemMapper itemMapper;

	@Autowired
	public TransactionMapper(ClientMapper clientMapper, StoreMapper storeMapper, ItemMapper itemMapper) {
		this.clientMapper = clientMapper;
		this.storeMapper = storeMapper;
		this.itemMapper = itemMapper;
	}

	public Iterable<TransactionDto> toTransactionsDto(Iterable<Transaction> transactions){
		Iterable<TransactionDto> transactionsDto = new ArrayList<>();
		for (Transaction transaction: transactions) {
			((ArrayList<TransactionDto>) transactionsDto).add(toTransactionDto(transaction));
		}
		return transactionsDto;
	}

	public TransactionDto toTransactionDto(Transaction transaction){
		return TransactionDto
				.builder()
				.id(transaction.getId())
				.client(clientMapper.toClientDto(transaction.getClient()))
				.store(storeMapper.toStoreDto(transaction.getStore()))
				.item(itemMapper.toItemDto(transaction.getItem()))
				.amount(transaction.getAmount())
				.date(transaction.getDate())
				.build();
	}

	public Transaction toTransaction(TransactionDto transactionDto){
		return Transaction
				.builder()
				.id(transactionDto.getId())
				.client(clientMapper.toClient(transactionDto.getClient()))
				.store(storeMapper.toStore(transactionDto.getStore()))
				.item(itemMapper.toItem(transactionDto.getItem()))
				.amount(transactionDto.getAmount())
				.date(transactionDto.getDate())
				.build();
	}
}
