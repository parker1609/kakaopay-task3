package com.parksungbum.kakaopaytask3.domain.exception;

public class NotFoundInstitutionException extends RuntimeException {
    private static final String MESSAGE = "찾을 수 없는 기관입니다.";

    public NotFoundInstitutionException() {
        super(MESSAGE);
    }
}
