package com.simpleweb.app.service;

import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.simpleweb.app.client.MockService;
import com.simpleweb.app.client.dto.ItemStock;
import com.simpleweb.app.entity.Transaction;
import com.simpleweb.app.exception.BadRequestException;
import com.simpleweb.app.repository.TransactionRepository;
import com.simpleweb.app.service.dto.ClientDto;
import com.simpleweb.app.service.dto.ItemDto;
import com.simpleweb.app.service.dto.ItemOrderDto;
import com.simpleweb.app.service.dto.OrderDto;
import com.simpleweb.app.service.dto.OrdersDto;
import com.simpleweb.app.service.dto.StoreDto;
import com.simpleweb.app.service.dto.TransactionDto;
import com.simpleweb.app.service.mapper.TransactionMapper;

@Service
public class TransactionServiceImpl implements TransactionService {

	private final MockService mockService;
	private final TransactionRepository transactionRepository;
	private final TransactionMapper transactionMapper;
	private final ClientService clientService;
	private final StoreService storeService;
	private final ItemService itemService;

	@Autowired
	public TransactionServiceImpl(MockService mockService, TransactionRepository transactionRepository, TransactionMapper transactionMapper, ClientService clientService, StoreService storeService, ItemService itemService) {
		this.mockService = mockService;
		this.transactionRepository = transactionRepository;
		this.transactionMapper = transactionMapper;
		this.clientService = clientService;
		this.storeService = storeService;
		this.itemService = itemService;
	}

	@Override
	public TransactionDto save(TransactionDto transactionDto) {
		Transaction transaction = transactionRepository.save(transactionMapper.toTransaction(transactionDto));
		return transactionMapper.toTransactionDto(transaction);
	}

	@Override
	public List<TransactionDto> findAllByDateBetweenAndClientDni(Date dateTransactionStart, Date dateTransactionEnd, String dni) {
		clientService.findByDni(dni);
		List<TransactionDto> transactionsDto = StreamSupport
				.stream(transactionMapper.toTransactionsDto(transactionRepository.findAllByDateBetweenAndClientDni(dateTransactionStart, dateTransactionEnd, dni)).spliterator(), false)
				.collect(Collectors.toList());
		return transactionsDto;
	}

	@Transactional
	@Override
	public void order(OrdersDto ordersDto, String dni) throws InterruptedException, ExecutionException {
		ClientDto clientDto = clientService.findByDni(dni);

		for (OrderDto orderDto: ordersDto.getOrders()) {
			StoreDto storeDto = storeService.findByCod(orderDto.getStoreCod());

			for (ItemOrderDto itemOrderDto: orderDto.getItems()) {
				ItemDto itemDto = itemService.findByCod(itemOrderDto.getCod());

				if (!storeDto.getItems().contains(itemDto)) {
					throw new BadRequestException("Item " + itemDto.getCod() + " not exists on " + storeDto.getCod());
				}

				int newStock = itemDto.getStock() - itemOrderDto.getAmount();

				if (newStock < -10) {
					throw(new BadRequestException("Stock not available (>10)"));
				}
				else if (-10 <= newStock && newStock < -5) {
					ItemStock itemStock = mockService.getTenStock();

					newStock = itemDto.getStock() + itemStock.getStock();
					itemDto.setStock(newStock);
					itemService.updateStock(itemDto.getCod(), itemDto);

					newStock = itemDto.getStock() - itemOrderDto.getAmount();
					itemDto.setStock(newStock);
				}
				else if (-5 <= newStock && newStock < 0) {
					newStock = itemDto.getStock() - itemOrderDto.getAmount();
					itemDto.setStock(newStock);
					itemService.updateStock(itemDto.getCod(), itemDto);

					Future<ItemStock> itemStock = mockService.getFiveStock();

					while (true) {
						if (itemStock.isDone()) {
							newStock = itemDto.getStock() + itemStock.get().getStock();
							itemDto.setStock(newStock);
							break;
				        }
				    }
				}
				else {
					itemDto.setStock(newStock);
				}

				itemService.updateStock(itemDto.getCod(), itemDto);

				TransactionDto transactionDto = TransactionDto.builder()
						.client(clientDto)
						.store(storeDto)
						.item(itemDto)
						.amount(itemOrderDto.getAmount())
						.date(new Date(System.currentTimeMillis()))
						.build();
				save(transactionDto);
			}
		}
	}
}
