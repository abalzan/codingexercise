package com.andrei.accela.codingexercise.controller;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

public abstract class BaseIT {

    private static final int HTTP_STATUS_OK = HttpStatus.OK.value();
    private static final int HTTP_STATUS_BAD_REQUEST = HttpStatus.BAD_REQUEST.value();
    private static final int HTTP_STATUS_NO_CONTENT = HttpStatus.NO_CONTENT.value();

    @Autowired
    WebApplicationContext webApplicationContext;

    public MockMvc mockMvc;

    @BeforeEach
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
    }

    MockHttpServletResponse response;

    protected MockHttpServletResponse sendPutRequestWithJson(String requestUri, String content) throws Exception {
        return mockMvc.perform(put(requestUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andReturn()
                .getResponse();
    }

    protected MockHttpServletResponse sendPostRequestWithJson(String requestUri, String content) throws Exception {
        return mockMvc.perform(post(requestUri)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content))
                .andDo(print())
                .andReturn()
                .getResponse();
    }

    protected MockHttpServletResponse sendGetRequest(String requestUri) throws Exception {
        return mockMvc.perform(get(requestUri))
                .andDo(print())
                .andReturn()
                .getResponse();
    }

    protected MockHttpServletResponse sendDeleteRequest(String requestUri) throws Exception {
        return mockMvc.perform(delete(requestUri))
                .andDo(print())
                .andReturn()
                .getResponse();
    }

    protected String getJsonAsString(String jsonFilePath) throws IOException {
        File resource = new ClassPathResource(jsonFilePath).getFile();
        return new String(Files.readAllBytes(resource.toPath()));
    }

    protected void assertResponseStatusIsOk() {
        assertEquals(HTTP_STATUS_OK, response.getStatus());
    }

    protected void assertResponseStatusIsBadRequest() {
        assertEquals(HTTP_STATUS_BAD_REQUEST, response.getStatus());
    }

    protected void assertResponseStatusIsNoContent() {
        assertEquals(HTTP_STATUS_NO_CONTENT, response.getStatus());
    }
}
