package com.simpleweb.app.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.data.repository.query.Param;

import com.simpleweb.app.service.dto.OrdersDto;
import com.simpleweb.app.service.dto.TransactionDto;

public interface TransactionService {

	public TransactionDto save(TransactionDto transactionDto);
	public List<TransactionDto> findAllByDateBetweenAndClientDni(@Param("dateTransactionStart") Date dateTransactionStart, @Param("dateTransactionEnd") Date dateTransactionEnd, String dni);
	void order(OrdersDto ordersDto, String dni) throws InterruptedException, ExecutionException;
}
