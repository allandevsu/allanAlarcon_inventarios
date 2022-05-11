package com.simpleweb.app.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.simpleweb.app.exception.ResourceNotFoundException;
import com.simpleweb.app.service.ItemService;
import com.simpleweb.app.service.dto.ItemDto;

@DisplayName("Item controller")
public class ItemControllerTest {

	@InjectMocks
	private ItemController itemController;

	@Mock
	private ItemService itemService;

	private ObjectWriter objectWriter;
	private MockMvc mockMvc;
	private final String relativePath = "/api/items";

	@BeforeEach
	void setUp() throws JsonProcessingException {
		openMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(itemController).build();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
		objectWriter = objectMapper.writer().withDefaultPrettyPrinter();
	}

	@Test
	@DisplayName("GET: " + relativePath + ": ✔ Status code 200")
	void getItems_statusOk() throws Exception {
		mockMvc.perform(
				get(relativePath)
				.characterEncoding("utf-8")
				)
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("PATCH: " + relativePath + ": ✔ Status code 200")
	void updateStock_statusOk() throws Exception {
		ItemDto itemDto = ItemDto.builder()
				.stock(15)
				.build();
		String requestJson = objectWriter.writeValueAsString(itemDto);
		when(itemService.updateStock(any(), any())).thenReturn(itemDto);
		mockMvc.perform(
				patch(relativePath + "/1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.characterEncoding("utf-8")
				)
		.andExpect(status().isOk());
	}

	@Test
	@DisplayName("PATCH: " + relativePath + ": ✔ Status code 400")
	void updateStock_statusBadRequest() throws Exception {
		ItemDto itemDto = ItemDto.builder()
				.stock(0)
				.build();
		String requestJson = objectWriter.writeValueAsString(itemDto);
		mockMvc.perform(
				patch(relativePath + "/1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.characterEncoding("utf-8")
				)
		.andExpect(status().is(400));
	}

	@Test
	@DisplayName("PATCH: " + relativePath + ": ✔ Status code 404")
	void updateStock_statusNotFound() throws Exception {
		ItemDto itemDto = ItemDto.builder()
				.stock(15)
				.build();
		String requestJson = objectWriter.writeValueAsString(itemDto);
		when(itemService.updateStock(any(), any())).thenThrow(ResourceNotFoundException.class);
		mockMvc.perform(
				patch(relativePath + "/1/stock")
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.characterEncoding("utf-8")
				)
		.andExpect(status().is(404));
	}
}
