package com.parksungbum.kakaopaytask3.support.util;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class CsvFileReader {

    public List<String[]> read(InputStream stream) throws IOException {
        List<String[]> results = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(stream))) {
            String[] nextLine;
            while ((nextLine = reader.readNext()) != null) {
                results.add(nextLine);
            }
        }

        return results;
    }
}
