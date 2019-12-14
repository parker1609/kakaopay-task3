package com.parksungbum.kakaopaytask3.domain;

import javax.persistence.*;

@Entity
public class Fund extends BaseEntity {

    @ManyToOne
    private HousingFinance housingFinance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    private int amount;

    public Fund(HousingFinance housingFinance, Institution institution, int amount) {
        this.housingFinance = housingFinance;
        this.institution = institution;
        this.amount = amount;
    }

    private Fund() {
    }

    public HousingFinance getHousingFinance() {
        return housingFinance;
    }

    public Institution getInstitution() {
        return institution;
    }

    public int getAmount() {
        return amount;
    }


}
