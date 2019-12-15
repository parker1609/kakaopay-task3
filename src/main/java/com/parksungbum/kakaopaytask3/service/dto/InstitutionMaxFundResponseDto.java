package com.parksungbum.kakaopaytask3.service.dto;

public class InstitutionMaxFundResponseDto {
    private int year;
    private String bank;

    public InstitutionMaxFundResponseDto(int year, String bank) {
        this.year = year;
        this.bank = bank;
    }

    public int getYear() {
        return year;
    }

    public String getBank() {
        return bank;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
