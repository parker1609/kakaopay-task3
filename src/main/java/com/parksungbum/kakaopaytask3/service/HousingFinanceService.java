package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.HousingFinanceRepository;
import com.parksungbum.kakaopaytask3.domain.Month;
import com.parksungbum.kakaopaytask3.domain.Year;
import org.springframework.stereotype.Service;

@Service
public class HousingFinanceService {

    private final HousingFinanceRepository housingFinanceRepository;

    public HousingFinanceService(HousingFinanceRepository housingFinanceRepository) {
        this.housingFinanceRepository = housingFinanceRepository;
    }

    public HousingFinance save(int year, int month) {
        HousingFinance housingFinance = new HousingFinance(new Year(year), new Month(month));
        return housingFinanceRepository.save(housingFinance);
    }
}
