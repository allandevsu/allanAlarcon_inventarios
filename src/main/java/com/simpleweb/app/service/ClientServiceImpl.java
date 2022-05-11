package com.simpleweb.app.service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.simpleweb.app.entity.Client;
import com.simpleweb.app.exception.BadRequestException;
import com.simpleweb.app.exception.ResourceNotFoundException;
import com.simpleweb.app.repository.ClientRepository;
import com.simpleweb.app.service.dto.ClientDto;
import com.simpleweb.app.service.mapper.ClientMapper;
import com.simpleweb.app.util.Image;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;
	private final ClientMapper clientMapper;
	

	@Autowired
	public ClientServiceImpl(ClientRepository clientRepository, ClientMapper clientMapper) {
		this.clientRepository = clientRepository;
		this.clientMapper = clientMapper;
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

}
