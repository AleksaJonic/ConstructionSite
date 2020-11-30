package com.lexsoft.project.constructions.components;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class ClientCallExecution {

    private String createUrl(String path, Integer port) {
        return "http://localhost:".concat(String.valueOf(port)).concat("/construction-site").concat(path);
    }

    protected <I,T> ResponseEntity<T> executeCall(String uriPath, Integer port, HttpMethod method, I body, TestRestTemplate template, Class<T> clazz) {

        HttpEntity<String> entity = new HttpEntity(body, new HttpHeaders());
        ResponseEntity<T> executionResponse =
                template.exchange(createUrl(uriPath, port), method, entity, clazz);
        return executionResponse;
    }

}
