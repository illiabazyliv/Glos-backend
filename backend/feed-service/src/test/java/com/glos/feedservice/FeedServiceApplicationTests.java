package com.glos.feedservice;

import com.glos.feedservice.domain.DTO.PageDTO;
import com.glos.feedservice.domain.DTO.RepositoryDTO;
import com.glos.feedservice.domain.filters.RepositoryFilter;
import com.glos.feedservice.domain.repositories.FeedRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static reactor.core.publisher.Mono.when;

@SpringBootTest
@AutoConfigureMockMvc
class FeedServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private FeedRepository feedRepository;
	@Test
	void contextLoads() throws Exception {
		RepositoryFilter filter = new RepositoryFilter();
		RepositoryDTO repositoryDTO = new RepositoryDTO();
		List<RepositoryDTO> repositoryDTOS = List.of(repositoryDTO);
		PageDTO<RepositoryDTO> page = new PageDTO<>();
		page.setContent(repositoryDTOS);

		Mockito.when(feedRepository.getStaticRepos(Mockito.any(RepositoryFilter.class))).thenReturn(page);

		mockMvc.perform(MockMvcRequestBuilders.get("/feed")
						.param("pageNumber", "0")
						.param("pageSize", "12")
						.param("sort", "id,asc")
						.accept(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().isOk());

		Mockito.verify(feedRepository, Mockito.times(1)).getStaticRepos(Mockito.any(RepositoryFilter.class));
	}

}
