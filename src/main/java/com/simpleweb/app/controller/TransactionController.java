package com.simpleweb.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simpleweb.app.service.TransactionService;
import com.simpleweb.app.service.dto.OrdersDto;
import com.simpleweb.app.service.dto.TransactionDto;
@RestController
@RequestMapping("api/transactions")
public class TransactionController {

	private final TransactionService transactionService;

	@Autowired
	public TransactionController(TransactionService transactionService) {
		this.transactionService = transactionService;
	}

	@PostMapping("/client/{dni}/order")
	@ResponseStatus(HttpStatus.OK)
	public void order(@RequestBody OrdersDto ordersDto, @PathVariable String dni) throws InterruptedException, ExecutionException{
		transactionService.order(ordersDto, dni);
	}

	@GetMapping("/client/{dni}/report")
	@ResponseStatus(HttpStatus.OK)
	public List<TransactionDto> report(@RequestParam String dateTransactionStart, @RequestParam String dateTransactionEnd, @PathVariable String dni) throws ParseException{
		return transactionService.findAllByDateBetweenAndClientDni(new SimpleDateFormat("yyyy-MM-dd").parse(dateTransactionStart), new SimpleDateFormat("yyyy-MM-dd").parse(dateTransactionEnd), dni);
	}

}
