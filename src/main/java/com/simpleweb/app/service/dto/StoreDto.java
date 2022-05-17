package com.simpleweb.app.service.dto;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDto {
	private Long id;

	@NotBlank
	private String cod;

	@NotBlank
	private String name;

	private Set<ItemDto> items = new HashSet<>();
}
