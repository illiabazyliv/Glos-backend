package com.glos.databaseAPIService;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class DatabaseApiServiceApplicationTests {

	private MockMvc mockMvc;

	public DatabaseApiServiceApplicationTests(MockMvc mockMvc) {
		this.mockMvc = mockMvc;
	}

	@Test
	void contextLoads() throws Exception {
		mockMvc.perform(get("/v1/files", request().attribute("id", 1L)))
				.andExpect(status().isOk());
	}

}
