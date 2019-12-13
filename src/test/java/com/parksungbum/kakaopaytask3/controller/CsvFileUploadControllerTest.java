package com.parksungbum.kakaopaytask3.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;

@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CsvFileUploadControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("클라이언트로부터 POST 요청을 통해 CSV 파일을 정상적으로 업로드한다.")
    void upload_csv_file() {
        MultipartBodyBuilder builder = new MultipartBodyBuilder();
        ClassPathResource classPathResource = new ClassPathResource("사전과제3.csv");
        builder.part("file", classPathResource).header("Content-type", "text/csv");

        webTestClient.post()
                .uri("/upload")
                .contentType(MediaType.MULTIPART_FORM_DATA)
                .body(BodyInserters.fromMultipartData(builder.build()))
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.fileName").isEqualTo("사전과제3.csv")
                .jsonPath("$.contentType").isEqualTo("text/csv")
                .jsonPath("$.size").isEqualTo(8887)
        ;
    }
}
