package com.simpleweb.app.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StoreDto {
	private Long id;
	private String cod;
	private String name;
}
