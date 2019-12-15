package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.fund.Fund;
import com.parksungbum.kakaopaytask3.domain.fund.FundRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import com.parksungbum.kakaopaytask3.service.dto.AnnualFundStatisticsResponseDto;
import com.parksungbum.kakaopaytask3.service.dto.AverageAmountAndYearResponseDto;
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

        return new InstitutionMaxFundResponseDto(
                yearAndInstitutionOfMaxFund.get(0)[0].toString(),
                yearAndInstitutionOfMaxFund.get(0)[1].toString()
        );
    }

    public List<AverageAmountAndYearResponseDto> findMaxAndMinAverageAmountInfoByBank(String bankName) {
        List<Object[]> minAverageAmountInfo = fundRepository.findMinAverageAmountByBank(bankName);
        List<Object[]> maxAverageAmountInfo = fundRepository.findMaxAverageAmountByBank(bankName);
        List<AverageAmountAndYearResponseDto> averageAmountAndYearResponses = new ArrayList<>();

        AverageAmountAndYearResponseDto minAverageAmountDto =
                new AverageAmountAndYearResponseDto(
                        minAverageAmountInfo.get(0)[0].toString(),
                        minAverageAmountInfo.get(0)[1].toString());
        AverageAmountAndYearResponseDto maxAverageAmountDto =
                new AverageAmountAndYearResponseDto(
                        maxAverageAmountInfo.get(0)[0].toString(),
                        maxAverageAmountInfo.get(0)[1].toString());

        averageAmountAndYearResponses.add(minAverageAmountDto);
        averageAmountAndYearResponses.add(maxAverageAmountDto);

        return averageAmountAndYearResponses;
    }
}
