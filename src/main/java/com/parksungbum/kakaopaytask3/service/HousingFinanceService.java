package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceRepository;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceTime;
import com.parksungbum.kakaopaytask3.domain.housingfinance.Month;
import com.parksungbum.kakaopaytask3.domain.housingfinance.Year;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class HousingFinanceService {
    private final HousingFinanceRepository housingFinanceRepository;

    public HousingFinanceService(final HousingFinanceRepository housingFinanceRepository) {
        this.housingFinanceRepository = housingFinanceRepository;
    }

    @Transactional()
    public HousingFinanceTime save(final int year, final int month) {
        HousingFinanceTime housingFinanceTime = new HousingFinanceTime(new Year(year), new Month(month));
        return housingFinanceRepository.save(housingFinanceTime);
    }
}
