package com.parksungbum.kakaopaytask3.domain;

import com.parksungbum.kakaopaytask3.domain.exception.NotFoundInstitutionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class InstitutionCodeTest {

    @Test
    @DisplayName("기관이 포함되어 있는 문자열을 입력으로 하였을 때 해당 기관 이름을 반환한다.")
    void convert_institution_name() {
        String data = "하나은행(억원)";

        assertThat(InstitutionCode.convertInstitutionName(data)).isEqualTo("하나은행");
    }

    @Test
    @DisplayName("기관이 포함되어 있는 문자열을 입력으로 하였을 때 해당 기관 코드를 반환한다.")
    void convert_institution_code() {
        String data = "하나은행(억원)";

        assertThat(InstitutionCode.convertInstitutionCode(data)).isEqualTo("bank05");
    }

    @Test
    @DisplayName("등록되어 있지 않는 기관의 이름이나 코드를 찾으려할 때 예외가 발생한다.")
    void not_found_institution_error() {
        String data = "카카오페이";

        assertThrows(NotFoundInstitutionException.class,
                () -> InstitutionCode.convertInstitutionName(data));
        assertThrows(NotFoundInstitutionException.class,
                () -> InstitutionCode.convertInstitutionCode(data));
    }
}
