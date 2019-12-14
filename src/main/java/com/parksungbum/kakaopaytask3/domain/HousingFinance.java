package com.parksungbum.kakaopaytask3.domain;

import javax.persistence.Entity;

@Entity
public class HousingFinance extends BaseEntity {
    private final int year;
    private final int month;

    public HousingFinance(int year, int month) {
        this.year = year;
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }
}
