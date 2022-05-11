package com.simpleweb.app.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.simpleweb.app.client.MockService;
import com.simpleweb.app.client.dto.ItemStock;
import com.simpleweb.app.entity.Client;
import com.simpleweb.app.exception.BadRequestException;
import com.simpleweb.app.exception.ResourceNotFoundException;
import com.simpleweb.app.repository.ClientRepository;
import com.simpleweb.app.service.dto.ClientDto;
import com.simpleweb.app.service.dto.ItemDto;
import com.simpleweb.app.service.dto.ItemOrderDto;
import com.simpleweb.app.service.dto.OrderDto;
import com.simpleweb.app.service.dto.OrdersDto;
import com.simpleweb.app.service.dto.StoreDto;
import com.simpleweb.app.service.dto.TransactionDto;
import com.simpleweb.app.service.mapper.ClientMapper;
import com.simpleweb.app.util.Image;

@Service
public class ClientServiceImpl implements ClientService {

	private final MockService mockService;
	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;
	private final StoreService storeService;
	private final ItemService itemService;
	private final TransactionService transactionService;

	@Autowired
	public ClientServiceImpl(MockService mockService, ClientRepository clientRepository, ClientMapper clientMapper, StoreService storeService, ItemService itemService, TransactionService transactionService) {
		this.mockService = mockService;
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
		this.storeService = storeService;
		this.itemService = itemService;
		this.transactionService = transactionService;
	}

	@Override
	public ClientDto save(ClientDto clientDto) {
		if (clientRepository.existsByDni(clientDto.getDni())) {
			throw(new BadRequestException("Client already exists"));
		}
		Client client = clientRepository.save(clientMapper.toClient(clientDto));
		return clientMapper.toClientDto(client);
	}

	@Override
	public List<ClientDto> findAll() {
		List<ClientDto> clientsDto = StreamSupport
				.stream(clientMapper.toClientsDto(clientRepository.findAll()).spliterator(), false)
				.collect(Collectors.toList());
		return clientsDto;
	}

	@Override
	public ClientDto findByDni(String dni) {
		Client client = clientRepository.findByDni(dni).orElseThrow(() -> new ResourceNotFoundException("Client not found: " + dni));
		return clientMapper.toClientDto(client);
	}

	@Override
	public ClientDto update(String dni, ClientDto clientDto) {
		Client client = clientRepository.findByDni(dni).orElseThrow(() -> new ResourceNotFoundException("Client not found: " + dni));

		if (clientDto.getDni() != null) {
			throw(new BadRequestException("Cannot change DNI"));
		}
		if (clientDto.getName() != null) {
			client.setName(clientDto.getName());
		}

		clientRepository.save(client);
		return clientMapper.toClientDto(client);
	}

	@Override
	@Transactional
	public void deleteByDni(String dni) {
		clientRepository.findByDni(dni).orElseThrow(() -> new ResourceNotFoundException("Client not found"));
		clientRepository.deleteByDni(dni);
	}

	@Override
	public void uploadImage(String dni, MultipartFile file) throws IOException {
		Client client = clientRepository.findByDni(dni).orElseThrow(() -> new ResourceNotFoundException("Client not found: " + dni));
		client.setPicture(Image.compressImage(file.getBytes()));
	}

	@Override
	public void order(OrdersDto ordersDto, String dni) throws InterruptedException, ExecutionException {
		ClientDto clientDto = findByDni(dni);

		for (OrderDto orderDto: ordersDto.getOrders()) {
			StoreDto storeDto = storeService.findByCod(orderDto.getStoreCod());

			for (ItemOrderDto itemOrderDto: orderDto.getItems()) {
				ItemDto itemDto = itemService.findByCod(itemOrderDto.getCod());

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
					itemService.updateStock(itemDto.getCod(), itemDto);
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
							itemService.updateStock(itemDto.getCod(), itemDto);
							break;
				        }
				    }
				}
				else {
					itemDto.setStock(newStock);
					itemService.updateStock(itemDto.getCod(), itemDto);
					TransactionDto transactionDto = TransactionDto.builder()
							.client(clientDto)
							.store(storeDto)
							.item(itemDto)
							.amount(itemOrderDto.getAmount())
							.date(new Date(System.currentTimeMillis()))
							.build();
					transactionService.save(transactionDto);
				}
			}
		}
	}

}
