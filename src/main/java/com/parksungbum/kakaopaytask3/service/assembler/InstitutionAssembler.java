package com.parksungbum.kakaopaytask3.service.assembler;

import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import com.parksungbum.kakaopaytask3.service.dto.InstitutionResponseDto;

public class InstitutionAssembler {

    public static InstitutionResponseDto toResponseDto(final Institution institution) {
        return new InstitutionResponseDto(institution.getName(), institution.getCode());
    }
}
