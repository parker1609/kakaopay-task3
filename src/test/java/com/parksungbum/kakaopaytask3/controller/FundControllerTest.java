package com.parksungbum.kakaopaytask3.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.hasItem;

public class FundControllerTest extends ControllerTestTemplate {

    @BeforeEach
    void setUp() {
        requestCsvFileUpload(CSV_FILE_NAME);
    }

    @Test
    @DisplayName("년도별 각 기관의 지원금 합계와 총 합계를 조회한다.")
    void show_amount_sum() {
        webTestClient.get().uri("/funds/statistics")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$..year").value(hasItem("2005"))
                .jsonPath("$..totalAmount").value(hasItem("48016"))
                .jsonPath("$..detailAmount").isArray()
                .jsonPath("$..detailAmount[*].name").value(hasItem("주택도시기금"))
                .jsonPath("$..detailAmount[*].amount").value(hasItem("22247"))
        ;
    }
}
