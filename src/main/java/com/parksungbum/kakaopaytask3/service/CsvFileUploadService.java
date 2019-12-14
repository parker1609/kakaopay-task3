package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import com.parksungbum.kakaopaytask3.domain.institution.InstitutionCode;
import com.parksungbum.kakaopaytask3.service.dto.FileUploadResponseDto;
import com.parksungbum.kakaopaytask3.support.parser.CsvFileParser;
import com.parksungbum.kakaopaytask3.support.parser.IntegerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CsvFileUploadService {
    private static final Logger log = LoggerFactory.getLogger(CsvFileUploadService.class);
    private static final int YEAR_INDEX = 0;
    private static final int MONTH_INDEX = 1;
    private static final int START_INSTITUTION_INDEX = 2;

    private final InstitutionService institutionService;
    private final HousingFinanceService housingFinanceService;
    private final FundService fundService;
    private final CsvFileParser csvFileParser;
    private final IntegerConverter integerConverter;

    public CsvFileUploadService(final InstitutionService institutionService,
                                final HousingFinanceService housingFinanceService,
                                final FundService fundService,
                                final CsvFileParser csvFileParser,
                                final IntegerConverter integerConverter) {
        this.institutionService = institutionService;
        this.housingFinanceService = housingFinanceService;
        this.fundService = fundService;
        this.csvFileParser = csvFileParser;
        this.integerConverter = integerConverter;
    }

    public FileUploadResponseDto save(MultipartFile csvFile) {
        List<String> header = getHeaderInCsvFile(csvFile);
        List<List<String>> body = getBodyInCsvFile(csvFile);

        saveInstitution(header);
        saveHousingFinanceAndFund(body);

        return new FileUploadResponseDto(
                csvFile.getOriginalFilename(),
                csvFile.getContentType(),
                csvFile.getSize());
    }

    private List<String> getHeaderInCsvFile(MultipartFile file) {
        try {
            return csvFileParser.getHeader(file.getInputStream());
        } catch (IOException e) {
            log.error("CSV file header parsing error : ", e);
            throw new IllegalArgumentException();
        }
    }

    private List<List<String>> getBodyInCsvFile(MultipartFile file) {
        try {
            return csvFileParser.getBody(file.getInputStream());
        } catch (IOException e) {
            log.error("CSV file body parsing error : ", e);
            throw new IllegalArgumentException();
        }
    }

    private void saveInstitution(List<String> header) {
        for (int i = START_INSTITUTION_INDEX; i < header.size(); ++i) {
            String institutionName = InstitutionCode.convertInstitutionName(header.get(i));
            String institutionCode = InstitutionCode.convertInstitutionCode(header.get(i));
            institutionService.save(institutionName, institutionCode);
        }
    }

    private void saveHousingFinanceAndFund(List<List<String>> body) {
        for (List<String> bodyRow : body) {
            int year = integerConverter.convert(bodyRow.get(YEAR_INDEX));
            int month = integerConverter.convert(bodyRow.get(MONTH_INDEX));
            HousingFinance housingFinance = housingFinanceService.save(year, month);
            createFundWithLine(bodyRow, housingFinance);
        }
    }

    private void createFundWithLine(List<String> bodyRow, HousingFinance housingFinance) {
        for (int i = START_INSTITUTION_INDEX; i < bodyRow.size(); i++) {
            // TODO: 2019/12/14 Institution Id 가져오는 것 좀 더 객체지향적으로 생각해볼 것
            Long institutionId = (long) (i - 1);
            Institution institution = institutionService.findById(institutionId);
            int amount = integerConverter.convert(bodyRow.get(i));
            fundService.save(housingFinance, institution, amount);
        }
    }
}
