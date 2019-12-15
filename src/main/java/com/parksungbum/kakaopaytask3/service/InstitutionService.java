package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.exception.NotFoundInstitutionException;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import com.parksungbum.kakaopaytask3.domain.institution.InstitutionRepository;
import com.parksungbum.kakaopaytask3.service.assembler.InstitutionAssembler;
import com.parksungbum.kakaopaytask3.service.dto.InstitutionResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InstitutionService {
    private final InstitutionRepository institutionRepository;

    public InstitutionService(final InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    @Transactional()
    public Institution save(final String institutionName, final String institutionCode) {
        Institution institution = new Institution(institutionName, institutionCode);
        return institutionRepository.save(institution);
    }

    @Transactional(readOnly = true)
    public Institution findById(final Long institutionId) {
        return institutionRepository.findById(institutionId)
                .orElseThrow(NotFoundInstitutionException::new);
    }

    @Transactional(readOnly = true)
    public List<InstitutionResponseDto> findAll() {
        return institutionRepository.findAll().stream()
                .map(InstitutionAssembler::toResponseDto)
                .collect(Collectors.toList())
                ;
    }
}
