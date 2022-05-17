package com.simpleweb.app.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

	@GetMapping(path="/client/{dni}/report", produces=MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	public byte[] report(@RequestParam String dateTransactionStart, @RequestParam String dateTransactionEnd, @PathVariable String dni) throws ParseException, IOException {

		List<TransactionDto> transactionsDto = transactionService.findAllByDateBetweenAndClientDni(
				new SimpleDateFormat("yyyy-MM-dd").parse(dateTransactionStart),
				new SimpleDateFormat("yyyy-MM-dd").parse(dateTransactionEnd),
				dni);

		try {
			CSVPrinter csvPrinter = new CSVPrinter(new FileWriter("report.csv"), CSVFormat.EXCEL.withHeader("Client", "Store", "Item", "Amount", "Date"));
			for (TransactionDto transactionDto : transactionsDto) {
				csvPrinter.printRecord(transactionDto.getClient().getName(), transactionDto.getStore().getName(),
						transactionDto.getItem().getName(), transactionDto.getAmount(), transactionDto.getDate());
			}
			csvPrinter.flush();

			File downloadCSV = new File("report.csv");
			InputStream input = new FileInputStream(downloadCSV);
			return IOUtils.toByteArray(input);

		} catch (IOException exception) {
			throw exception;
		}
	}

}
