package com.parksungbum.kakaopaytask3.service.dto;

public class InstitutionTotalAmountDto {
    private String name;
    private int amount;

    public InstitutionTotalAmountDto(String name, int amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public int getAmount() {
        return amount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
