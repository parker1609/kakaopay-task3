package com.parksungbum.kakaopaytask3.domain.fund;

import com.parksungbum.kakaopaytask3.domain.BaseEntity;
import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinanceTime;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;

import javax.persistence.*;

@Entity
public class Fund extends BaseEntity {

    @ManyToOne
    private HousingFinanceTime housingFinanceTime;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "institution_id")
    private Institution institution;

    private int amount;

    public Fund(final HousingFinanceTime housingFinanceTime, final Institution institution, final int amount) {
        this.housingFinanceTime = housingFinanceTime;
        this.institution = institution;
        this.amount = amount;
    }

    private Fund() {
    }

    public HousingFinanceTime getHousingFinanceTime() {
        return housingFinanceTime;
    }

    public Institution getInstitution() {
        return institution;
    }

    public int getAmount() {
        return amount;
    }


}
