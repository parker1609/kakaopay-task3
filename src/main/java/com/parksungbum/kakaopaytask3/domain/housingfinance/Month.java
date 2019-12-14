package com.parksungbum.kakaopaytask3.domain.housingfinance;

import com.parksungbum.kakaopaytask3.domain.exception.InvalidMonthException;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Month {
    private static final int MIN_MONTH = 1;
    private static final int MAX_MONTH = 12;

    @Column(nullable = false, length = 10)
    private int month;

    public Month(int month) {
        validate(month);
        this.month = month;
    }

    private Month() {
    }

    private void validate(int month) {
        if (MIN_MONTH > month || MAX_MONTH < month) {
            throw new InvalidMonthException();
        }
    }

    public int getMonth() {
        return month;
    }
}
