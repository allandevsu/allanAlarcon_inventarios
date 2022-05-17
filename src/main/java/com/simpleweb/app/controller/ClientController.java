package com.simpleweb.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.simpleweb.app.service.ClientService;
import com.simpleweb.app.service.dto.ClientDto;

@RestController
@RequestMapping("api/clients")
public class ClientController {

	private final ClientService clientService;

	@Autowired
	public ClientController(ClientService clientService) {
		this.clientService = clientService;
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ClientDto create(@Valid @RequestBody ClientDto clientDto){
		return clientService.save(clientDto);
	}

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public List<ClientDto> list(){
		return clientService.findAll();
	}

	@GetMapping("/{dni}")
	@ResponseStatus(HttpStatus.OK)
	public ClientDto get(@PathVariable String dni){
		return clientService.findByDni(dni);
	}

	@PutMapping("/{dni}")
	@ResponseStatus(HttpStatus.OK)
	public ClientDto update(@RequestBody ClientDto clientDto, @PathVariable String dni){
		return clientService.update(dni, clientDto);
	}

	@DeleteMapping("/{dni}")
	@ResponseStatus(HttpStatus.OK)
	public void delete(@PathVariable String dni){
		clientService.deleteByDni(dni);
	}

}
