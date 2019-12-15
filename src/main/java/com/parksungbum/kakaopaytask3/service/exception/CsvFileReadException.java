package com.parksungbum.kakaopaytask3.service.exception;

public class CsvFileReadException extends RuntimeException {
    private static final String MESSAGE = "CSV 파일 읽기가 실패하였습니다.";

    public CsvFileReadException(final Throwable e) {
        super(MESSAGE, e);
    }
}
