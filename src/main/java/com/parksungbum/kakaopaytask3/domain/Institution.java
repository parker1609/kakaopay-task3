package com.parksungbum.kakaopaytask3.domain;

import javax.persistence.Entity;

@Entity
public class Institution extends BaseEntity {
    private String name;
    private String code;

    public Institution(String name, String code) {
        this.name = name;
        this.code = code;
    }

    private Institution() {
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }
}
