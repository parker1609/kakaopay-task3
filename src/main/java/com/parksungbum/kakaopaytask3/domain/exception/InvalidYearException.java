package com.parksungbum.kakaopaytask3.domain.exception;

public class InvalidYearException extends RuntimeException {
    private static final String MESSAGE = "유효하지 않는 Year 입니다.";

    public InvalidYearException() {
        super(MESSAGE);
    }
}
