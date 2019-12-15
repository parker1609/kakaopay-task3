package com.parksungbum.kakaopaytask3.service.dto;

public class InstitutionTotalAmountDto {
    private String name;
    private String amount;

    public InstitutionTotalAmountDto(String name, String amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public String getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
