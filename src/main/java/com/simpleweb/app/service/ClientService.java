package com.simpleweb.app.service;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.web.multipart.MultipartFile;

import com.simpleweb.app.service.dto.ClientDto;
import com.simpleweb.app.service.dto.OrdersDto;

public interface ClientService {

	public ClientDto save(ClientDto clientDto);
	public List<ClientDto> findAll();
	public ClientDto findByDni(String dni);
	public ClientDto update(String dni, ClientDto clientDto);
	public void deleteByDni(String dni);
	public void uploadImage(String dni, MultipartFile file) throws IOException;
	public void order(OrdersDto ordersDto, String dni) throws InterruptedException, ExecutionException;
}
