package com.parksungbum.kakaopaytask3.service.exception;

public class DoesNotMatchYearException extends RuntimeException {
    private static final String MESSAGE = "일치하는 연도가 없습니다.";

    public DoesNotMatchYearException() {
        super(MESSAGE);
    }
}
