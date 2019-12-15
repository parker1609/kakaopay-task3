package com.parksungbum.kakaopaytask3.service.dto;

public class InstitutionMaxFundResponseDto {
    private String year;
    private String bank;

    public InstitutionMaxFundResponseDto(String year, String bank) {
        this.year = year;
        this.bank = bank;
    }

    public String getYear() {
        return year;
    }

    public String getBank() {
        return bank;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}
