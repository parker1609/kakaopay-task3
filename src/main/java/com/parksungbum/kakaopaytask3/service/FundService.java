package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.fund.Fund;
import com.parksungbum.kakaopaytask3.domain.fund.FundRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceTime;
import com.parksungbum.kakaopaytask3.domain.housingfinance.Year;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import com.parksungbum.kakaopaytask3.domain.institution.InstitutionCode;
import com.parksungbum.kakaopaytask3.service.dto.*;
import org.apache.commons.math3.stat.regression.SimpleRegression;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class FundService {
    private static final int START_YEAR = 2005;
    private static final int END_YEAR = 2017;
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
        List<AnnualFundStatisticsResponseDto> annualFundStatistics = new ArrayList<>();

        for (int i = START_YEAR; i <= END_YEAR; ++i) {
            Year currentYear = new Year(i);
            int totalAmount = fundRepository.findALLByHousingFinanceTime_Year(currentYear).stream()
                    .map(Fund::getAmount)
                    .reduce(0, Integer::sum)
                    ;

            List<InstitutionTotalAmountDto> institutionTotalAmountDtos =
                    createInstitutionTotalAmounts(currentYear);

            AnnualFundStatisticsResponseDto annualFundStatisticsResponseDto =
                    new AnnualFundStatisticsResponseDto(i, totalAmount, institutionTotalAmountDtos);
            annualFundStatistics.add(annualFundStatisticsResponseDto);
        }

        return annualFundStatistics;
    }

    private List<InstitutionTotalAmountDto> createInstitutionTotalAmounts(Year currentYear) {
        List<InstitutionTotalAmountDto> institutionTotalAmountDtos = new ArrayList<>();
        for (InstitutionCode value : InstitutionCode.values()) {
            String institutionName = value.getName();
            int totalAmountByInstitution =
                    fundRepository.findAllByHousingFinanceTime_YearAndInstitution_Name(currentYear, institutionName)
                            .stream()
                            .map(Fund::getAmount)
                            .reduce(0, Integer::sum)
                    ;

            InstitutionTotalAmountDto institutionTotalAmountDto =
                    new InstitutionTotalAmountDto(institutionName, totalAmountByInstitution);
            institutionTotalAmountDtos.add(institutionTotalAmountDto);
        }

        return institutionTotalAmountDtos;
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
