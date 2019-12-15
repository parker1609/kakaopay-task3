package com.parksungbum.kakaopaytask3.support;

import com.parksungbum.kakaopaytask3.support.util.IntegerConverter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class IntegerConverterTest {

    private IntegerConverter integerConverter = new IntegerConverter();

    @Test
    @DisplayName("숫자가 아닌 문자가 앞에 있는 경우 숫자로 변경한다.")
    void convert_integer_prefix() {
        String data = "\"10";

        assertThat(integerConverter.convert(data)).isEqualTo(10);
    }

    @Test
    @DisplayName("숫자가 아닌 문자가 뒤에 있는 경우 숫자로 변경한다.")
    void convert_integer_suffix() {
        String data = "10\"";

        assertThat(integerConverter.convert(data)).isEqualTo(10);
    }

    @Test
    @DisplayName("숫자가 아닌 문자가 여러 곳에 있는 경우 숫자로 변경한다.")
    void convert_integer() {
        String data = "\'1,000\"";

        assertThat(integerConverter.convert(data)).isEqualTo(1000);
    }
}
