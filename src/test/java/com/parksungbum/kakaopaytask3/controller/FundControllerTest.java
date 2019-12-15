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
    void show_annual_fund_statistics() {
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

    @Test
    @DisplayName("각 년도별 각 기관의 전체 지원금액 중에서 가장 큰 금액의 기관명을 조회한다.")
    void show_institution_and_year_of_max_fund() {
        webTestClient.get().uri("/funds/maximum/institution")
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.year").isEqualTo("2014")
                .jsonPath("$.bank").isEqualTo("주택도시기금")
        ;
    }
}
