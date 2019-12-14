package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.Institution;
import com.parksungbum.kakaopaytask3.domain.InstitutionRepository;
import com.parksungbum.kakaopaytask3.domain.exception.NotFoundInstitutionException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
public class InstitutionServiceTest {
    private static final String INSTITUTION_NAME = "카카오페이";
    private static final String INSTITUTION_CODE = "bank11";
    private static final Long INSTITUTION_ID = 1L;

    @InjectMocks
    private InstitutionService institutionService;

    @Mock
    private InstitutionRepository institutionRepository;

    @Mock
    private Institution institution;

    @Test
    @DisplayName("Institution 엔티티를 정상적으로 저장한다.")
    void save_institution() {
        given(institutionRepository.save(any(Institution.class))).willReturn(institution);

        institutionService.save(INSTITUTION_NAME, INSTITUTION_CODE);

        verify(institutionRepository).save(any(Institution.class));
    }

    @Test
    @DisplayName("Institution 객체 하나를 정상적으로 조회한다.")
    void find_institution() {
        given(institutionRepository.findById(any(Long.class))).willReturn(Optional.of(institution));

        institutionService.findById(INSTITUTION_ID);

        verify(institutionRepository).findById(INSTITUTION_ID);
    }

    @Test
    @DisplayName("찾을 수 없는 Institution 객체를 조회하는 경우 예외를 발생한다.")
    void find_institution_error() {
        given(institutionRepository.findById(any(Long.class))).willReturn(Optional.empty());

        assertThrows(NotFoundInstitutionException.class,
                () -> institutionService.findById(INSTITUTION_ID));
    }
}
