package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.fund.Fund;
import com.parksungbum.kakaopaytask3.domain.fund.FundRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import com.parksungbum.kakaopaytask3.service.dto.AnnualFundStatisticsResponseDto;
import com.parksungbum.kakaopaytask3.service.dto.InstitutionMaxFundResponseDto;
import com.parksungbum.kakaopaytask3.service.dto.InstitutionTotalAmountDto;
import com.parksungbum.kakaopaytask3.service.exception.DoesNotMatchYearException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundService {

    private final FundRepository fundRepository;

    public FundService(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    public Fund save(HousingFinance housingFinance, Institution institution, int amount) {
        Fund fund = new Fund(housingFinance, institution, amount);

        return fundRepository.save(fund);
    }

    public List<AnnualFundStatisticsResponseDto> findAllAnnualFundStatistics() {
        List<Object[]> annualTotalFunds = fundRepository.findAllAnnualTotalFund();
        List<AnnualFundStatisticsResponseDto> annualFundStatistics =
                assembleAnnualTotalFunds(annualTotalFunds);

        List<Object[]> annualInstitutionFunds = fundRepository.findAllAnnualInstitutionFund();
        assembleAnnualInstitutionFunds(annualFundStatistics, annualInstitutionFunds);

        return annualFundStatistics;
    }

    private List<AnnualFundStatisticsResponseDto> assembleAnnualTotalFunds(List<Object[]> annualTotalFunds) {
        List<AnnualFundStatisticsResponseDto> annualFundStatistics = new ArrayList<>();

        for (Object[] annualTotalFund : annualTotalFunds) {
            AnnualFundStatisticsResponseDto annualFundStatisticsResponseDto = new AnnualFundStatisticsResponseDto();
            annualFundStatisticsResponseDto.setYear(annualTotalFund[0].toString());
            annualFundStatisticsResponseDto.setTotalAmount(annualTotalFund[1].toString());
            annualFundStatistics.add(annualFundStatisticsResponseDto);
        }

        return annualFundStatistics;
    }

    private void assembleAnnualInstitutionFunds(List<AnnualFundStatisticsResponseDto> annualFundStatistics,
                                                List<Object[]> annualInstitutionFunds) {
        for (Object[] institutionFund : annualInstitutionFunds) {
            AnnualFundStatisticsResponseDto currentYearSupportAmount =
                    annualFundStatistics.stream()
                            .filter(dto -> dto.getYear().equals(institutionFund[0].toString()))
                            .findAny()
                            .orElseThrow(DoesNotMatchYearException::new);

            InstitutionTotalAmountDto institutionTotalAmountDto =
                    new InstitutionTotalAmountDto(institutionFund[1].toString(), institutionFund[2].toString());
            currentYearSupportAmount.getDetailAmount().add(institutionTotalAmountDto);
        }
    }

    public InstitutionMaxFundResponseDto findYearAndInstitutionOfMaxFund() {
        List<Object[]> yearAndInstitutionOfMaxFund = fundRepository.findYearAndInstitutionOfMaxFund();
        List<InstitutionMaxFundResponseDto> institutionMaxFundResponses = new ArrayList<>();

        for (Object[] institutionInfos : yearAndInstitutionOfMaxFund) {
            InstitutionMaxFundResponseDto institutionInfo =
                    new InstitutionMaxFundResponseDto(
                            institutionInfos[0].toString(),
                            institutionInfos[1].toString());
            institutionMaxFundResponses.add(institutionInfo);
        }

        return institutionMaxFundResponses.get(0);
    }
}
