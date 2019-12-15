package com.parksungbum.kakaopaytask3.controller;

import com.parksungbum.kakaopaytask3.service.InstitutionService;
import com.parksungbum.kakaopaytask3.service.dto.InstitutionResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class InstitutionController {
    private final InstitutionService institutionService;

    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @GetMapping("/institutions")
    public ResponseEntity<List<InstitutionResponseDto>> showAll() {
        List<InstitutionResponseDto> institutions = institutionService.findAll();

        return ResponseEntity.ok(institutions);
    }
}
