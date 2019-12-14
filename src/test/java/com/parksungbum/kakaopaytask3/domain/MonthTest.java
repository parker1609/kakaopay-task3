package com.parksungbum.kakaopaytask3.domain;

import com.parksungbum.kakaopaytask3.domain.exception.InvalidMonthException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MonthTest {

    @Test
    @DisplayName("정상적으로 Month 객체를 생성한다.")
    void create_month() {
        assertDoesNotThrow(() -> new Month(12));
    }

    @Test
    @DisplayName("최소 달보다 작은 숫자로 Month 객체를 생성할 때 오류가 발생한다.")
    void create_min_error() {
        assertThrows(InvalidMonthException.class, () -> new Month(0));
    }

    @Test
    @DisplayName("최대 달보다 큰 숫자로 Month 객체를 생성할 때 오류가 발생한다.")
    void create_max_error() {
        assertThrows(InvalidMonthException.class, () -> new Month(13));
    }
}
