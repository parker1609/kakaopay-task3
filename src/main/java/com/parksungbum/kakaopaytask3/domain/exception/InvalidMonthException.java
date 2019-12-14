package com.parksungbum.kakaopaytask3.domain.exception;

public class InvalidMonthException extends RuntimeException {
    private static final String MESSAGE = "유효하지 않는 Month 입니다.";

    public InvalidMonthException() {
        super(MESSAGE);
    }
}
