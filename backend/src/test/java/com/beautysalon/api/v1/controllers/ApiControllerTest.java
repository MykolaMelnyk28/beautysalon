package com.beautysalon.api.v1.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ApiController.class)
@ExtendWith(MockitoExtension.class)
class ApiControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    void healthShouldReturn200StatusAndOKMessage()
            throws Exception {
        mvc.perform(get("/health"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value("OK"));
    }

    @Test
    void versionShouldReturn200StatusAndVersionApi() throws Exception {
        final String version = "1.0.0";

        mvc.perform(get("/version"))
                .andExpect(status().isOk())
                .andExpect(content().string("API BeautySalon " + version));
    }
}