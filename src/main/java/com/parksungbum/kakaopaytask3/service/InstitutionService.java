package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.Institution;
import com.parksungbum.kakaopaytask3.domain.InstitutionRepository;

public class InstitutionService {

    private final InstitutionRepository institutionRepository;

    public InstitutionService(InstitutionRepository institutionRepository) {
        this.institutionRepository = institutionRepository;
    }

    public void save(String institutionName, String institutionCode) {
        Institution institution = new Institution(institutionName, institutionCode);
        institutionRepository.save(institution);
    }
}
