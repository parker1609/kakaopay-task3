package com.parksungbum.kakaopaytask3.service.dto;

import java.util.ArrayList;
import java.util.List;

public class AnnualFundStatisticsResponseDto {
    private int year;
    private int totalAmount;
    private List<InstitutionTotalAmountDto> detailAmount = new ArrayList<>();

    public int getYear() {
        return year;
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public List<InstitutionTotalAmountDto> getDetailAmount() {
        return detailAmount;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDetailAmount(List<InstitutionTotalAmountDto> detailAmount) {
        this.detailAmount = detailAmount;
    }
}
