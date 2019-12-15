package com.parksungbum.kakaopaytask3.service.dto;

public class PredictFundDto {
    private String bank;
    private int year;
    private int month;
    private int amount;

    public PredictFundDto(String bank, int year, int month, int amount) {
        this.bank = bank;
        this.year = year;
        this.month = month;
        this.amount = amount;
    }

    public String getBank() {
        return bank;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getAmount() {
        return amount;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
