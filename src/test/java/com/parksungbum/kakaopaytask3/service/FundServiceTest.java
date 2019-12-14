package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.Fund;
import com.parksungbum.kakaopaytask3.domain.FundRepository;
import com.parksungbum.kakaopaytask3.domain.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.Institution;
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
public class FundServiceTest {

    private static final int AMOUNT = 3000;

    @InjectMocks
    private FundService fundService;

    @Mock
    private FundRepository fundRepository;

    @Mock
    private Fund fund;

    @Mock
    private HousingFinance housingFinance;

    @Mock
    private Institution institution;

    @Test
    @DisplayName("Fund 엔티티를 정상적으로 저장한다.")
    void save_institution() {
        given(fundRepository.save(any(Fund.class))).willReturn(fund);

        fundService.save(housingFinance, institution, AMOUNT);

        verify(fundRepository).save(any(Fund.class));
    }
}
