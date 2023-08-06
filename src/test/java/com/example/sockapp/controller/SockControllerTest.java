package com.example.sockapp.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SockControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private JSONObject testSock = new JSONObject();

    @BeforeEach
    void setUp() throws JSONException {
        testSock.put("id", 1);
        testSock.put("color", "color");
        testSock.put("cottonPart", 45);
        testSock.put("quantity", 111);
    }

    @Test
    void income() throws Exception {
        mockMvc.perform(
                        post("/api/socks/income")
                                .queryParam("color", "color")
                                .queryParam("cottonPart", "45")
                                .queryParam("quantity", "111")
                                .contentType(MediaType.APPLICATION_JSON).content(testSock.toString()))
                .andExpect(status().isOk());
    }
    @Test
    void incomeExep() throws Exception {
        mockMvc.perform(
                        post("/api/socks/income")
                                .queryParam("color", "color")
                                .queryParam("cottonPart", "45")
                                .queryParam("quantity", "-10")
                                .contentType(MediaType.APPLICATION_JSON).content(testSock.toString()))
                .andExpect(status().is(400));
    }

    @Test
    void outcome() throws Exception {
        mockMvc.perform(
                        post("/api/socks/outcome")
                                .queryParam("color", "color")
                                .queryParam("cottonPart", "45")
                                .queryParam("quantity", "111")
                                .contentType(MediaType.APPLICATION_JSON).content(testSock.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void getSock() throws Exception {
        mockMvc.perform(
                        get("/api/socks/")
                                .queryParam("color", "color")
                                .queryParam("comparison", "equal")
                                .queryParam("cottonPart", "111")
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}
