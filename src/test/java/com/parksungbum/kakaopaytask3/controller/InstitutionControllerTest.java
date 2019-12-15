package com.parksungbum.kakaopaytask3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItem;

public class InstitutionControllerTest extends ControllerTestTemplate {

    @BeforeEach
    void setUp() {
        requestCsvFileUpload(CSV_FILE_NAME);
    }

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
                .jsonPath("$..name").value(hasItem("국민은행"))
                .jsonPath("$..code").value(hasItem("bank01"))
                ;
    }
}
