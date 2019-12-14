package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceRepository;
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
public class HousingFinanceServiceTest {
    private static final int YEAR = 2019;
    private static final int MONTH = 12;

    @InjectMocks
    private HousingFinanceService housingFinanceService;

    @Mock
    private HousingFinanceRepository housingFinanceRepository;

    @Mock
    private HousingFinance housingFinance;

    @Test
    @DisplayName("HousingFinance 엔티티를 정상적으로 저장한다.")
    void save_institution() {
        given(housingFinanceRepository.save(any(HousingFinance.class))).willReturn(housingFinance);

        housingFinanceService.save(YEAR, MONTH);

        verify(housingFinanceRepository).save(any(HousingFinance.class));
    }
}
