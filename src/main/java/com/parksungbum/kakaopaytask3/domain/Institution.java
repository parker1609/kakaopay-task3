package com.parksungbum.kakaopaytask3.domain;

import javax.persistence.Entity;

@Entity
public class Institution extends BaseEntity {
    private final String name;
    private final String code;

    public Institution(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
