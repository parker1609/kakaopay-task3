package com.parksungbum.kakaopaytask3.service.dto;

public class AverageAmountAndYearResponseDto {
    private String year;
    private String amount;

    public AverageAmountAndYearResponseDto(String year, String amount) {
        this.year = year;
        this.amount = amount;
    }

    public String getYear() {
        return year;
    }

    public String getAmount() {
        return amount;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
