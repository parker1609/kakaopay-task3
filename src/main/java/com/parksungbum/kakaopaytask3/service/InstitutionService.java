package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.Institution;
import com.parksungbum.kakaopaytask3.domain.InstitutionRepository;
import org.springframework.stereotype.Service;

@Service
public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public Institution save(String institutionName, String institutionCode) {
        Institution institution = new Institution(institutionName, institutionCode);
        return institutionRepository.save(institution);
    }

    public Institution findById(Long institutionId) {
        return institutionRepository.findById(institutionId)
                .orElseThrow(IllegalArgumentException::new);
    }
}
