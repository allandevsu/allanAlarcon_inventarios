package com.simpleweb.app.service.dto;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
	private Long id;

	@NotBlank(message="DNI cannot be empty")
	private String dni;

	@NotBlank(message="Name cannot be empty")
	private String name;
	private byte[] picture;
}
