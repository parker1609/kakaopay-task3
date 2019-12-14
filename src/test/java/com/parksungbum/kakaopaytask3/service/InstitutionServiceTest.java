package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.Institution;
import com.parksungbum.kakaopaytask3.domain.InstitutionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class InstitutionServiceTest {
    private static final String INSTITUTION_NAME = "카카오페이";
    private static final String INSTITUTION_CODE = "bank11";

    @InjectMocks
    private InstitutionService institutionService;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private Institution institution;

    @Test
    @DisplayName("CSV 파일로부터 Institution 엔티티를 정상적으로 저장한다.")
    void save_institution() {
        given(institutionRepository.save(institution)).willReturn(institution);

        institutionService.save(INSTITUTION_NAME, INSTITUTION_CODE);

        verify(institutionRepository).save(any(Institution.class));
    }
}
