package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.fund.Fund;
import com.parksungbum.kakaopaytask3.domain.fund.FundRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import org.springframework.stereotype.Service;

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
}
