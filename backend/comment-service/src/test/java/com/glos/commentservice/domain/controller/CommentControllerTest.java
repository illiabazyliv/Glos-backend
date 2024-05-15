package com.glos.commentservice.domain.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import static org.junit.jupiter.api.Assertions.*;
@WebMvcTest(CommentController.class)
@ExtendWith(MockitoExtension.class)
class CommentControllerTest {
    @Test
    void testCreateComment() {
    }
}