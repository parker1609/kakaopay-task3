package com.parksungbum.kakaopaytask3.support.util;

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

    public List<String> parseHeader(final List<String[]> csvFileData) {
        String[] header = csvFileData.get(HEADER_START_INDEX);
        int startIndexWithoutSpace = getNumberOfSpaceInPrefix(header);
        int endIndexWithoutSpace = header.length - getNumberOfSpaceInSuffix(header);

        return new ArrayList<>(Arrays.asList(header)
                .subList(startIndexWithoutSpace, endIndexWithoutSpace));
    }

    private int getNumberOfSpaceInPrefix(final String[] rows) {
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

    private int getNumberOfSpaceInSuffix(final String[] rows) {
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

    public List<List<String>> parseBody(final List<String[]> csvFileData) {
        List<List<String>> body = new ArrayList<>(new ArrayList<>());

        for (String[] row : csvFileData.subList(BODY_START_INDEX, csvFileData.size())) {
            int startIndexWithoutSpace = getNumberOfSpaceInPrefix(row);
            int endIndexWithoutSpace = row.length - getNumberOfSpaceInSuffix(row);

            body.add(new ArrayList<>(Arrays.asList(row)
                    .subList(startIndexWithoutSpace, endIndexWithoutSpace)));
        }

        return body;
    }
}
