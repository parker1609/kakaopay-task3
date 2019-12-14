package com.parksungbum.kakaopaytask3.support;

import com.parksungbum.kakaopaytask3.support.parser.CsvFileParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class CsvFileParserTest {

    private ClassPathResource classPathResource;
    private CsvFileParser csvFileParser = new CsvFileParser();

    @BeforeEach
    void setUp() {
        classPathResource = new ClassPathResource("사전과제3.csv");
    }

    @Test
    @DisplayName("CSV 파일의 헤더 값들이 정상적으로 파싱되는지 확인한다.")
    void parse_csv_file_and_get_headers() throws IOException {
        List<String> header = csvFileParser.getHeader(classPathResource.getInputStream());

        assertThat(header.get(0)).isEqualTo("연도");
        assertThat(header.get(1)).isEqualTo("월");
        assertThat(header.get(2)).isEqualTo("주택도시기금1)(억원)");
        assertThat(header.get(3)).isEqualTo("국민은행(억원)");
        assertThat(header.get(4)).isEqualTo("우리은행(억원)");
        assertThat(header.get(5)).isEqualTo("신한은행(억원)");
        assertThat(header.get(6)).isEqualTo("한국시티은행(억원)");
        assertThat(header.get(7)).isEqualTo("하나은행(억원)");
        assertThat(header.get(8)).isEqualTo("농협은행/수협은행(억원)");
        assertThat(header.get(9)).isEqualTo("외환은행(억원)");
        assertThat(header.get(10)).isEqualTo("기타은행(억원)");
        assertThat(header.size()).isEqualTo(11);
    }

    @Test
    @DisplayName("CSV 파일의 내용들이 정상적으로 파싱되는지 확인한다.")
    void parse_csv_file_get_body() throws IOException {
        List<List<String>> body = csvFileParser.getBody(classPathResource.getInputStream());

        for (List<String> bodyRow : body) {
            assertThat(bodyRow.size()).isEqualTo(11);
        }
    }
}
