package com.parksungbum.kakaopaytask3.domain;

import com.parksungbum.kakaopaytask3.domain.exception.NotFoundInstitutionException;

import java.util.stream.Stream;

public enum InstitutionCode {
    NHUF("주택도시기금", "public01"),
    KB("국민은행", "bank01"),
    WOORI("우리은행", "bank02"),
    SHINHAN("신한은행", "bank03"),
    KOREA_CITY("한국시티은행", "bank04"),
    HANA("하나은행", "bank05"),
    NH_SH("농협은행/수협은행", "bank06"),
    KEB("외환은행", "bank08"),
    ETC_BANK("기타은행", "bank99");

    private final String name;
    private final String code;

    InstitutionCode(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public static String convertInstitutionName(String name) {
        return Stream.of(values())
                .filter(code -> code.containsName(name))
                .findAny()
                .map(InstitutionCode::getName)
                .orElseThrow(NotFoundInstitutionException::new)
                ;
    }

    public static String convertInstitutionCode(String name) {
        return Stream.of(values())
                .filter(code -> code.containsName(name))
                .findAny()
                .map(InstitutionCode::getCode)
                .orElseThrow(NotFoundInstitutionException::new)
                ;
    }

    public boolean containsName(String name) {
        return name.contains(this.name);
    }
}
