package com.simpleweb.app.service.mapper;

import java.util.ArrayList;

import org.springframework.stereotype.Component;

import com.simpleweb.app.entity.Client;
import com.simpleweb.app.service.dto.ClientDto;

@Component
public class ClientMapper {

	public Iterable<ClientDto> toClientsDto(Iterable<Client> clients){
		Iterable<ClientDto> clientsDto = new ArrayList<>();
		for (Client client: clients) {
			((ArrayList<ClientDto>) clientsDto).add(toClientDto(client));
		}
		return clientsDto;
	}

	public ClientDto toClientDto(Client client){
		return ClientDto
				.builder()
				.id(client.getId())
				.dni(client.getDni())
				.name(client.getName())
				.build();
	}

	public Client toClient(ClientDto clientDto){
		return Client
				.builder()
				.id(clientDto.getId())
				.dni(clientDto.getDni())
				.name(clientDto.getName())
				.build();
	}
}
