package com.parksungbum.kakaopaytask3.domain.fund;

import com.parksungbum.kakaopaytask3.domain.BaseEntity;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;

import javax.persistence.*;

@Entity
public class Fund extends BaseEntity {

    @ManyToOne
    private HousingFinance housingFinance;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    private int amount;

    public Fund(final HousingFinance housingFinance, final Institution institution, final int amount) {
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
