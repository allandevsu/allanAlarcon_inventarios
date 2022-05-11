package com.simpleweb.app.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.simpleweb.app.service.dto.ClientDto;

public interface ClientService {

	public ClientDto save(ClientDto clientDto);
	public List<ClientDto> findAll();
	public ClientDto findByDni(String dni);
	public ClientDto update(String dni, ClientDto clientDto);
	public void deleteByDni(String dni);
	public void uploadImage(String dni, MultipartFile file) throws IOException;
}
