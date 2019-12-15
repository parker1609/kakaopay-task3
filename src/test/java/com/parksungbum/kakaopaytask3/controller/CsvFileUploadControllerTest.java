package com.parksungbum.kakaopaytask3.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

public class CsvFileUploadControllerTest extends ControllerTestTemplate {

    @Test
    @DisplayName("클라이언트로부터 POST 요청을 통해 CSV 파일을 정상적으로 업로드한다.")
    void upload_csv_file() {
        requestCsvFileUpload(CSV_FILE_NAME)
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
