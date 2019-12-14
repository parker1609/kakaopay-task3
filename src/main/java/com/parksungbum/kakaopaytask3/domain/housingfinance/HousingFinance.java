package com.parksungbum.kakaopaytask3.domain.housingfinance;

import com.parksungbum.kakaopaytask3.domain.BaseEntity;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class HousingFinance extends BaseEntity {

    @Embedded
    private Year year;

    @Embedded
    private Month month;

    public HousingFinance(Year year, Month month) {
        this.year = year;
        this.month = month;
    }

    private HousingFinance() {
    }

    public Year getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }
}
