package com.simpleweb.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.simpleweb.app.entity.Transaction;
import com.simpleweb.app.repository.TransactionRepository;
import com.simpleweb.app.service.dto.TransactionDto;
import com.simpleweb.app.service.mapper.TransactionMapper;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;

	@Autowired
	public TransactionServiceImpl(TransactionRepository transactionRepository, TransactionMapper transactionMapper) {
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
	}

	@Override
	public TransactionDto save(TransactionDto transactionDto) {
		Transaction transaction = transactionRepository.save(transactionMapper.toTransaction(transactionDto));
		return transactionMapper.toTransactionDto(transaction);
	}

}
