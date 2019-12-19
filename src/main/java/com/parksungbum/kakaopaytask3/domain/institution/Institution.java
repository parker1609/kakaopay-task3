package com.parksungbum.kakaopaytask3.domain.institution;

import com.parksungbum.kakaopaytask3.domain.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Institution extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 10)
    private String code;

    public Institution(final String name, final String code) {
        this.name = name;
        this.code = code;
    }

    public Institution() {
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
