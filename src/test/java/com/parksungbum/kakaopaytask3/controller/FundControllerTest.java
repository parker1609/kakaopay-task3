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
        webTestClient.get().uri("/funds/statistics/years")
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
        webTestClient.get().uri("/funds/maximum/years")
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

    @Test
    @DisplayName("전체 기간에서 입력한 은행 중 지원 금액 평균이 가장 클 때와 작을 때 연도와 지원 금액을 조회한다.")
    void show_year_and_amount_of_max_min_average_fund_by_bank() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/funds/average/maximum-minimum/years")
                        .queryParam("bank", "국민은행").build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.[0].year").isEqualTo("2006")
                .jsonPath("$.[0].amount").isEqualTo("484")
                .jsonPath("$.[1].year").isEqualTo("2016")
                .jsonPath("$.[1].amount").isEqualTo("5115")
        ;
    }

    @Test
    void name() {
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/funds/predict")
                        .queryParam("bank", "국민은행")
                        .queryParam("month", 2)
                        .build())
                .exchange()
                .expectStatus()
                .isOk()
                .expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.bank").isEqualTo("bank01")
                .jsonPath("$.year").isEqualTo("2018")
                .jsonPath("$.month").isEqualTo("2")
                .jsonPath("$.amount").isEqualTo("4817")
        ;
    }
}
