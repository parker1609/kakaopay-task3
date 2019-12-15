package com.parksungbum.kakaopaytask3.support.config;

import com.parksungbum.kakaopaytask3.support.util.CsvFileParser;
import com.parksungbum.kakaopaytask3.support.util.CsvFileReader;
import com.parksungbum.kakaopaytask3.support.util.IntegerConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebConfiguration {

    @Bean
    public CsvFileReader csvFileReaderConfig() {
        return new CsvFileReader();
    }

    @Bean
    public CsvFileParser fileParserConfig() {
        return new CsvFileParser();
    }

    @Bean
    public IntegerConverter integerConverterConfig() {
        return new IntegerConverter();
    }
}
