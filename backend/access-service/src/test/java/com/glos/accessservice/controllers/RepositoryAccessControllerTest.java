package com.glos.accessservice.controllers;

import com.glos.accessservice.facade.RepositoryApiFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RepositoryAccessController.class)
@ExtendWith(MockitoExtension.class)
class RepositoryAccessControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RepositoryApiFacade repositoryApiFacade;

    @Test
    void appendRepositoryAccessTypeTest()throws Exception {
         String rootFullName = "exampleRepo";
         String name = "read";
        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/{rootFullName}/append-access-type/{name}", rootFullName, name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
        Mockito.verify(repositoryApiFacade, Mockito.times(1)).repositoryAppendAccessType(rootFullName, name);
    }

    @Test
    void removeRepositoryAccessTypeTest() throws Exception {
        String rootFullName = "exampleRepo";
        String name = "read";
        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/{rootFullName}/remove-access-type/{name}", rootFullName, name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}