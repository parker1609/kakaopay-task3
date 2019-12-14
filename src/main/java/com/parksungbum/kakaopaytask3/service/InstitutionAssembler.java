package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.Institution;

public class InstitutionAssembler {

    public static InstitutionResponseDto toResponseDto(Institution institution) {
        return new InstitutionResponseDto(institution.getName(), institution.getCode());
    }
}
