package com.parksungbum.kakaopaytask3.support.parser;

import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CsvFileParser {
    private static final int HEADER_START_INDEX = 0;
    private static final int BODY_START_INDEX = 1;
    private static final String SPACES_REGEX = "^[\\s]*$";
    private static final Pattern spacePattern = Pattern.compile(SPACES_REGEX);

    public List<String> getHeader(InputStream stream) throws IOException {
        List<String[]> results = parse(stream);
        String[] header = results.get(HEADER_START_INDEX);
        int startIndexWithoutSpace = getNumberOfSpaceInPrefix(header);
        int endIndexWithoutSpace = header.length - getNumberOfSpaceInSuffix(header);

        return new ArrayList<>(Arrays.asList(header)
                .subList(startIndexWithoutSpace, endIndexWithoutSpace));
    }

    private int getNumberOfSpaceInPrefix(String[] rows) {
        int numberOfSpaceInPrefix = 0;

        for (String row : rows) {
            Matcher matcher = spacePattern.matcher(row);
            if (!"".equals(row) && !matcher.find()) {
                break;
            }

            numberOfSpaceInPrefix++;
        }

        return numberOfSpaceInPrefix;
    }

    private int getNumberOfSpaceInSuffix(String[] rows) {
        int numberOfSpaceInSuffix = 0;

        for (int index = rows.length - 1; index >= 0; --index) {
            String row = rows[index];
            Matcher matcher = spacePattern.matcher(row);
            if (!"".equals(row) && !matcher.find()) {
                break;
            }

            numberOfSpaceInSuffix++;
        }

        return numberOfSpaceInSuffix;
    }

    public List<List<String>> getBody(InputStream stream) throws IOException {
        List<String[]> results = parse(stream);
        List<List<String>> body = new ArrayList<>(new ArrayList<>());

        for (String[] row : results.subList(BODY_START_INDEX, results.size())) {
            int startIndexWithoutSpace = getNumberOfSpaceInPrefix(row);
            int endIndexWithoutSpace = row.length - getNumberOfSpaceInSuffix(row);

            body.add(new ArrayList<>(Arrays.asList(row)
                    .subList(startIndexWithoutSpace, endIndexWithoutSpace)));
        }

        return body;
    }

    public List<String[]> parse(InputStream stream) throws IOException {
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
