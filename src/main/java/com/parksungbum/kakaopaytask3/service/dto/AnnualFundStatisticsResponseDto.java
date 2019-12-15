package com.parksungbum.kakaopaytask3.service.dto;

import java.util.ArrayList;
import java.util.List;

public class AnnualFundStatisticsResponseDto {
    private String year;
    private String totalAmount;
    private List<InstitutionTotalAmountDto> detailAmount = new ArrayList<>();

    public String getYear() {
        return year;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public List<InstitutionTotalAmountDto> getDetailAmount() {
        return detailAmount;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setDetailAmount(List<InstitutionTotalAmountDto> detailAmount) {
        this.detailAmount = detailAmount;
    }
}
