package com.simpleweb.app.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDto {
	private Long id;

	@NotBlank
	private String cod;

	@NotBlank
	private String name;
	private Double price;

	@Positive
	private int stock;
}
