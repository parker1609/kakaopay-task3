package com.parksungbum.kakaopaytask3.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InstitutionControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @DisplayName("기관 조회 요청을 통해 전체 기관의 이름과 코드를 조회한다.")
    void read_institutions() {
        webTestClient.get().uri("/institutions")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$..name").isEqualTo("국민은행")
                .jsonPath("$..code").isEqualTo("bank01")
                ;
    }
}
