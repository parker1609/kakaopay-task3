package com.parksungbum.kakaopaytask3.service.dto;

public class AverageAmountAndYearResponseDto {
    private int year;
    private int amount;

    public AverageAmountAndYearResponseDto(int year, int amount) {
        this.year = year;
        this.amount = amount;
    }

    public int getYear() {
        return year;
    }

    public int getAmount() {
        return amount;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
