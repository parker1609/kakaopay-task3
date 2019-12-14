package com.parksungbum.kakaopaytask3.domain;

import javax.persistence.Embedded;
import javax.persistence.Entity;

@Entity
public class HousingFinance extends BaseEntity {

    @Embedded
    private final Year year;

    @Embedded
    private final Month month;

    public HousingFinance(Year year, Month month) {
        this.year = year;
        this.month = month;
    }

    public Year getYear() {
        return year;
    }

    public Month getMonth() {
        return month;
    }
}
