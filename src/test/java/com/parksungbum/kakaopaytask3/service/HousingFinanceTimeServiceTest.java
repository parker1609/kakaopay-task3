package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceTime;
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
public class HousingFinanceTimeServiceTest {
    private static final int YEAR = 2019;
    private static final int MONTH = 12;

    @InjectMocks
    private HousingFinanceService housingFinanceService;

    @Mock
    private HousingFinanceRepository housingFinanceRepository;

    @Mock
    private HousingFinanceTime housingFinanceTime;

    @Test
    @DisplayName("HousingFinance 엔티티를 정상적으로 저장한다.")
    void save_institution() {
        given(housingFinanceRepository.save(any(HousingFinanceTime.class))).willReturn(housingFinanceTime);

        housingFinanceService.save(YEAR, MONTH);

        verify(housingFinanceRepository).save(any(HousingFinanceTime.class));
    }
}
