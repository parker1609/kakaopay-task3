package com.parksungbum.kakaopaytask3.domain;

import com.parksungbum.kakaopaytask3.domain.exception.InvalidYearException;
import com.parksungbum.kakaopaytask3.domain.housingfinance.Year;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class YearTest {

    @Test
    @DisplayName("정상적으로 Year 객체를 생성한다.")
    void create_year() {
        assertDoesNotThrow(() -> new Year(2019));
    }

    @Test
    @DisplayName("최소 연도보다 작은 숫자로 Year 객체를 생성할 때 오류가 발생한다.")
    void create_min_error() {
        assertThrows(InvalidYearException.class, () -> new Year(1800));
    }

    @Test
    @DisplayName("최대 연도보다 큰 숫자로 Year 객체를 생성할 때 오류가 발생한다.")
    void create_max_error() {
        assertThrows(InvalidYearException.class, () -> new Year(2021));
    }
}
