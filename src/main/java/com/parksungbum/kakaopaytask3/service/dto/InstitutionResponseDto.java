package com.parksungbum.kakaopaytask3.service.dto;

public class InstitutionResponseDto {
    private String name;
    private String code;

    public InstitutionResponseDto(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
