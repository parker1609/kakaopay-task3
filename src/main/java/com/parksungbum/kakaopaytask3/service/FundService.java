package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.Fund;
import com.parksungbum.kakaopaytask3.domain.FundRepository;
import com.parksungbum.kakaopaytask3.domain.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.Institution;
import org.springframework.stereotype.Service;

@Service
public class FundService {

    private final FundRepository fundRepository;

    public FundService(FundRepository fundRepository) {
        this.fundRepository = fundRepository;
    }

    public void save(HousingFinance housingFinance, Institution institution, int amount) {
        Fund fund = new Fund(housingFinance, institution, amount);
        fundRepository.save(fund);
    }
}
