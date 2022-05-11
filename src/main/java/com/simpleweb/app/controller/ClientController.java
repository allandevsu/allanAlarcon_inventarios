package com.simpleweb.app.controller;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.simpleweb.app.exception.BadRequestException;
import com.simpleweb.app.service.ClientService;
import com.simpleweb.app.service.dto.ClientDto;
import com.simpleweb.app.service.dto.OrdersDto;

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
	public ClientDto create(@RequestBody ClientDto clientDto){
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

	@PostMapping("/{dni}/image")
	@ResponseStatus(HttpStatus.OK)
	public void uploadImage(@RequestParam("image") MultipartFile file, @PathVariable String dni){
		try {
			clientService.uploadImage(dni, file);
		} catch (IOException e) {
			throw(new BadRequestException("Error to upload"));
		}
	}

	@PostMapping("/{dni}/order")
	@ResponseStatus(HttpStatus.OK)
	public void order(@RequestBody OrdersDto ordersDto, @PathVariable String dni) throws InterruptedException, ExecutionException{
		clientService.order(ordersDto, dni);
	}

}
