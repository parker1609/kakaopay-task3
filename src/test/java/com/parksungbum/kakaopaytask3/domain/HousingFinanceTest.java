package com.parksungbum.kakaopaytask3.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class HousingFinanceTest {

    @Test
    @DisplayName("정상적으로 HousingFinance 객체를 생성한다.")
    void create() {
        Year year = new Year(2019);
        Month month = new Month(12);

        assertDoesNotThrow(() -> new HousingFinance(year, month));
    }
}
