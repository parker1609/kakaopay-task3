package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.fund.Fund;
import com.parksungbum.kakaopaytask3.domain.fund.FundRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinance;
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

    @Test
    @DisplayName("년도별 지원 금액 통계를 정상적으로 조회한다.")
    void find_annual_fund_statistics() {
        fundService.findAllAnnualFundStatistics();

        verify(fundRepository).findAllAnnualTotalFund();
        verify(fundRepository).findAllAnnualInstitutionFund();
    }

    @Test
    @DisplayName("두 쿼리 결과를 DTO로 합치는 도중 일치하는 년도가 없을 때 예외가 발생한다.")
    void find_annaul_fund_statistics_error_does_not_match_year() {
        // TODO: 2019/12/15 예외 테스트할 수 있는지 생각해볼 것
    }
}
