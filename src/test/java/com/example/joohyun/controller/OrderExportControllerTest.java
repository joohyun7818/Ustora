package com.example.joohyun.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class OrderExportControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("인증 토큰 없으면 403")
    void withoutToken() throws Exception {
        mockMvc.perform(get("/api/export/orders"))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("올바른 토큰으로 200")
    void withToken() throws Exception {
        mockMvc.perform(get("/api/export/orders")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer CHANGE_ME_SECURE_TOKEN"))
                .andExpect(status().isOk());
    }
}
