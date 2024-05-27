package com.glos.accessservice.controllers;
import com.glos.accessservice.facade.FileApiFacade;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
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