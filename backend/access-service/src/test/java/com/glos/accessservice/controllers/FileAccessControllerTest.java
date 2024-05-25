package com.glos.accessservice.controllers;
import com.glos.accessservice.clients.AccessTypeApiClient;
import com.glos.accessservice.facade.FileApiFacade;
import com.glos.accessservice.responseDTO.AccessTypesRequestFilter;
import com.glos.accessservice.responseDTO.Page;
import com.glos.api.entities.AccessType;
import com.glos.accessservice.utils.MapUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
@WebMvcTest(FileAccessController.class)
@ExtendWith(MockitoExtension.class)
class FileAccessControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FileApiFacade fileApiFacade;
    @Test
    void AppendFileAccessTypeTest() throws Exception {
        String rootFullName = "testRootFullName";
        String name = "read";

        mockMvc.perform(MockMvcRequestBuilders.put("/files/{rootFullName}/append-access-type/{name}", rootFullName, name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void RemoveFileAccessTypeTest() throws Exception {
        String rootFullName = "testRootFullName";
        String name = "testName";

        mockMvc.perform(MockMvcRequestBuilders.put("/files/{rootFullName}/remove-access-type/{name}", rootFullName, name)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}