package com.parksungbum.kakaopaytask3.domain.housingfinance;

import com.parksungbum.kakaopaytask3.domain.exception.InvalidYearException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Year {
    private static final int MIN_YEAR = 1900;
    private static final int MAX_YEAR = 2020;

    @Column(nullable = false, length = 10)
    private int year;

    public Year(int year) {
        validate(year);
        this.year = year;
    }

    private Year() {
    }

    private void validate(int year) {
        if (MIN_YEAR > year || MAX_YEAR < year) {
            throw new InvalidYearException();
        }
    }

    public int getYear() {
        return year;
    }
}
