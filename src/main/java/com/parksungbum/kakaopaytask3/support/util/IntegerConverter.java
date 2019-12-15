package com.parksungbum.kakaopaytask3.support.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IntegerConverter {

    public int convert(String data) {
        Pattern pattern = Pattern.compile("\\D");
        Matcher matcher = pattern.matcher(data);
        data = matcher.replaceAll("");
        return Integer.parseInt(data);
    }
}
