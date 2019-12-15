package com.parksungbum.kakaopaytask3.support.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerConverter {
    private static final String NOT_NUMBER_REGEX = "\\D";
    private static final String NONE = "";

    public int convert(String data) {
        Pattern pattern = Pattern.compile(NOT_NUMBER_REGEX);
        Matcher matcher = pattern.matcher(data);
        data = matcher.replaceAll(NONE);

        return Integer.parseInt(data);
    }
}
