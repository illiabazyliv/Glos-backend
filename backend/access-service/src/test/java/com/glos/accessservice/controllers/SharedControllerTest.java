package com.glos.accessservice.controllers;

import com.glos.accessservice.responseDTO.SharedTokenResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SharedController.class)
@ExtendWith(MockitoExtension.class)
class SharedControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private SharedFacade sharedFacade;

        @Test
        public void GenerateSharedTokenTest() throws Exception {
            String rootFullName = "testRootFullName";
            SharedTokenResponse expectedResponse = new SharedTokenResponse();

            when(sharedFacade.generate(anyString())).thenReturn(ResponseEntity.ok(expectedResponse));

            mockMvc.perform(MockMvcRequestBuilders.post("/{rootFullName}", rootFullName)
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(content().json("{/* JSON representation of expectedResponse */}"));
        }
}