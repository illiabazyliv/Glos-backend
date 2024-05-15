package com.glos.commentservice;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glos.commentservice.domain.DTO.UserDTO;
import com.glos.commentservice.domain.client.ExternalCommentApi;
import com.glos.commentservice.domain.entities.Comment;
import com.glos.commentservice.domain.entities.User;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentServiceApplicationTests.class)
@AutoConfigureMockMvc
class CommentServiceApplicationTests {
	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private ExternalCommentApi externalCommentApi;
	@Test
	void contextLoadsTest() throws Exception{
		Long id = 1L;
		User user = new User();
		user.setId(id);
		user.setUsername("asdf");

		Comment comment = new Comment();
		comment.setAuthor(user);
		comment.setText("Test Text");

		Comment created = new Comment();

		when(externalCommentApi.create(Mockito.any(Comment.class))).thenReturn(ResponseEntity.ok(created));

		mockMvc.perform(MockMvcRequestBuilders
						.post("/comments")
						.content(new ObjectMapper().writeValueAsString(comment))
						.contentType(MediaType.APPLICATION_JSON)
						.accept(MediaType.APPLICATION_JSON));
	}

}
