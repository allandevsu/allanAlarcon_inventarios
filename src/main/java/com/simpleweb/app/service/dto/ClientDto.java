package com.simpleweb.app.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClientDto {
	private Long id;
	private String dni;
	private String name;
}
