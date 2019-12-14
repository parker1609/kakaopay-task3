package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.HousingFinanceRepository;
import org.springframework.stereotype.Service;

@Service
public class HousingFinanceService {

    private final HousingFinanceRepository housingFinanceRepository;

    public HousingFinanceService(HousingFinanceRepository housingFinanceRepository) {
        this.housingFinanceRepository = housingFinanceRepository;
    }

    public void save(int year, int month) {
        HousingFinance housingFinance = new HousingFinance(year, month);
        housingFinanceRepository.save(housingFinance);
    }
}
