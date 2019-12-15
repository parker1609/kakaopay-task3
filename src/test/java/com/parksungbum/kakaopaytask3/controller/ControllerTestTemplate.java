package com.parksungbum.kakaopaytask3.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ControllerTestTemplate {
    public static final String CSV_FILE_NAME = "사전과제3.csv";

    @Autowired
    protected WebTestClient webTestClient;

    protected WebTestClient.ResponseSpec requestCsvFileUpload(String fileName) {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        ClassPathResource classPathResource = new ClassPathResource(fileName);
        builder.part("file", classPathResource).header("Content-type", "text/csv");

        return webTestClient.post()
                .uri("/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                ;
    }
}
