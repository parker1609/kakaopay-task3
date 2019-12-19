package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.fund.Fund;
import com.parksungbum.kakaopaytask3.domain.fund.FundRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceTime;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
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
    private HousingFinanceTime housingFinanceTime;

    @Mock
    private Institution institution;

    @Test
    @DisplayName("Fund 엔티티를 정상적으로 저장한다.")
    void save_institution() {
        given(fundRepository.save(any(Fund.class))).willReturn(fund);

        fundService.save(housingFinanceTime, institution, AMOUNT);

        verify(fundRepository).save(any(Fund.class));
    }

    @Test
    @DisplayName("년도별 지원 금액 통계를 정상적으로 조회한다.")
    void find_annual_fund_statistics() {
        fundService.findAllAnnualFundStatistics();

        verify(fundRepository).findAllAnnualTotalFund();
        verify(fundRepository).findAllAnnualInstitutionFund();
    }
}
