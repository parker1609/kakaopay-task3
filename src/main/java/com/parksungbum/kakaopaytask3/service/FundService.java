package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.fund.Fund;
import com.parksungbum.kakaopaytask3.domain.fund.FundRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceTime;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import com.parksungbum.kakaopaytask3.domain.institution.InstitutionCode;
import com.parksungbum.kakaopaytask3.service.dto.*;
import com.parksungbum.kakaopaytask3.service.exception.DoesNotMatchYearException;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundService {
    public static final int NEXT_YEAR = 2018;
    private final FundRepository fundRepository;

    public FundService(final FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    public Fund save(HousingFinanceTime housingFinanceTime, Institution institution, int amount) {
        Fund fund = new Fund(housingFinanceTime, institution, amount);

        return fundRepository.save(fund);
    }

    @Transactional(readOnly = true)
    public List<AnnualFundStatisticsResponseDto> findAllAnnualFundStatistics() {
        List<Object[]> annualTotalFunds = fundRepository.findAllAnnualTotalFund();
        List<AnnualFundStatisticsResponseDto> annualFundStatistics =
                assembleAnnualTotalFunds(annualTotalFunds);

        List<Object[]> annualInstitutionFunds = fundRepository.findAllAnnualInstitutionFund();
        assembleAnnualInstitutionFunds(annualFundStatistics, annualInstitutionFunds);

        return annualFundStatistics;
    }

    private List<AnnualFundStatisticsResponseDto> assembleAnnualTotalFunds(final List<Object[]> annualTotalFunds) {
        List<AnnualFundStatisticsResponseDto> annualFundStatistics = new ArrayList<>();

        for (Object[] annualTotalFund : annualTotalFunds) {
            AnnualFundStatisticsResponseDto annualFundStatisticsResponseDto = new AnnualFundStatisticsResponseDto();
            annualFundStatisticsResponseDto.setYear(Integer.parseInt(annualTotalFund[0].toString()));
            annualFundStatisticsResponseDto.setTotalAmount(Integer.parseInt(annualTotalFund[1].toString()));
            annualFundStatistics.add(annualFundStatisticsResponseDto);
        }

        return annualFundStatistics;
    }

    private void assembleAnnualInstitutionFunds(final List<AnnualFundStatisticsResponseDto> annualFundStatistics,
                                                final List<Object[]> annualInstitutionFunds) {
        for (Object[] institutionFund : annualInstitutionFunds) {
            AnnualFundStatisticsResponseDto currentYearSupportAmount =
                    annualFundStatistics.stream()
                            .filter(dto -> dto.getYear() == Integer.parseInt(institutionFund[0].toString()))
                            .findAny()
                            .orElseThrow(DoesNotMatchYearException::new);

            InstitutionTotalAmountDto institutionTotalAmountDto =
                    new InstitutionTotalAmountDto(institutionFund[1].toString(),
                            Integer.parseInt(institutionFund[2].toString()));
            currentYearSupportAmount.getDetailAmount().add(institutionTotalAmountDto);
        }
    }

    @Transactional(readOnly = true)
    public InstitutionMaxFundResponseDto findYearAndInstitutionOfMaxFund() {
        List<Object[]> yearAndInstitutionOfMaxFund = fundRepository.findYearAndInstitutionOfMaxFund();

        return new InstitutionMaxFundResponseDto(
                Integer.parseInt(yearAndInstitutionOfMaxFund.get(0)[0].toString()),
                yearAndInstitutionOfMaxFund.get(0)[1].toString()
        );
    }

    @Transactional(readOnly = true)
    public List<AverageAmountAndYearResponseDto> findMaxAndMinAverageAmountInfoByBank(final String bankName) {
        List<Object[]> minAverageAmountInfo = fundRepository.findMinAverageAmountByBank(bankName);
        List<Object[]> maxAverageAmountInfo = fundRepository.findMaxAverageAmountByBank(bankName);
        List<AverageAmountAndYearResponseDto> averageAmountAndYearResponses = new ArrayList<>();

        AverageAmountAndYearResponseDto minAverageAmountDto =
                new AverageAmountAndYearResponseDto(
                        Integer.parseInt(minAverageAmountInfo.get(0)[0].toString()),
                        Integer.parseInt(minAverageAmountInfo.get(0)[1].toString()));
        AverageAmountAndYearResponseDto maxAverageAmountDto =
                new AverageAmountAndYearResponseDto(
                        Integer.parseInt(maxAverageAmountInfo.get(0)[0].toString()),
                        Integer.parseInt(maxAverageAmountInfo.get(0)[1].toString()));

        averageAmountAndYearResponses.add(minAverageAmountDto);
        averageAmountAndYearResponses.add(maxAverageAmountDto);

        return averageAmountAndYearResponses;
    }

    @Transactional(readOnly = true)
    public PredictFundDto findPredictFund(final String bankName, final int month) {
        SimpleRegression simpleRegression = new SimpleRegression(true);

        List<Object[]> yearAndMonthAndAmountByBanks = fundRepository.findYearAndMonthAndAmountByBank(bankName);
        int index = 0;
        for (Object[] yearAndMonthAndAmountByBank : yearAndMonthAndAmountByBanks) {
            String amount = yearAndMonthAndAmountByBank[2].toString();
            simpleRegression.addData(index++, Double.parseDouble(amount));
        }

        for (int i = 0; i < month - 1; ++i) {
            simpleRegression.addData(index, simpleRegression.predict(index));
            index++;
        }

        int predictAmount = (int) simpleRegression.predict(index);
        return new PredictFundDto(InstitutionCode.convertInstitutionCode(bankName),
                NEXT_YEAR, month, predictAmount);
    }
}
