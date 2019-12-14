package com.parksungbum.kakaopaytask3.support.config;

import com.parksungbum.kakaopaytask3.support.parser.CsvFileParser;
import com.parksungbum.kakaopaytask3.support.parser.IntegerConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public CsvFileParser fileParserConfig() {
        return new CsvFileParser();
    }

    @Bean
    public IntegerConverter integerConverterConfig() {
        return new IntegerConverter();
    }
}
