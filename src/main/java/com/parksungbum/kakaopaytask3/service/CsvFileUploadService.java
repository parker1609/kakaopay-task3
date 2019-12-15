package com.parksungbum.kakaopaytask3.service;

import com.parksungbum.kakaopaytask3.domain.housingfinance.HousingFinance;
import com.parksungbum.kakaopaytask3.domain.institution.Institution;
import com.parksungbum.kakaopaytask3.domain.institution.InstitutionCode;
import com.parksungbum.kakaopaytask3.service.dto.FileUploadResponseDto;
import com.parksungbum.kakaopaytask3.service.exception.CsvFileReadException;
import com.parksungbum.kakaopaytask3.support.util.CsvFileParser;
import com.parksungbum.kakaopaytask3.support.util.CsvFileReader;
import com.parksungbum.kakaopaytask3.support.util.IntegerConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
    private final CsvFileReader csvFileReader;
    private final CsvFileParser csvFileParser;
    private final IntegerConverter integerConverter;

    public CsvFileUploadService(final InstitutionService institutionService,
                                final HousingFinanceService housingFinanceService,
                                final FundService fundService,
                                final CsvFileReader csvFileReader,
                                final CsvFileParser csvFileParser,
                                final IntegerConverter integerConverter) {
        this.institutionService = institutionService;
        this.housingFinanceService = housingFinanceService;
        this.fundService = fundService;
        this.csvFileReader = csvFileReader;
        this.csvFileParser = csvFileParser;
        this.integerConverter = integerConverter;
    }

    @Transactional
    public FileUploadResponseDto save(final MultipartFile csvFile) {
        List<String[]> csvFileRows = getCsvFileData(csvFile);
        List<String> header = csvFileParser.parseHeader(csvFileRows);
        List<List<String>> body = csvFileParser.parseBody(csvFileRows);

        saveInstitution(header);
        saveHousingFinanceAndFund(body);

        return new FileUploadResponseDto(
                csvFile.getOriginalFilename(),
                csvFile.getContentType(),
                csvFile.getSize());
    }

    private List<String[]> getCsvFileData(final MultipartFile file) {
        try {
            return csvFileReader.read(file.getInputStream());
        } catch (IOException e) {
            log.error("CSV file read error : ", e);
            throw new CsvFileReadException(e);
        }
    }

    private void saveInstitution(final List<String> header) {
        for (int i = START_INSTITUTION_INDEX; i < header.size(); ++i) {
            String institutionName = InstitutionCode.convertInstitutionName(header.get(i));
            String institutionCode = InstitutionCode.convertInstitutionCode(header.get(i));
            institutionService.save(institutionName, institutionCode);
        }
    }

    private void saveHousingFinanceAndFund(final List<List<String>> body) {
        for (List<String> bodyRow : body) {
            int year = integerConverter.convert(bodyRow.get(YEAR_INDEX));
            int month = integerConverter.convert(bodyRow.get(MONTH_INDEX));
            HousingFinance housingFinance = housingFinanceService.save(year, month);
            createFundWithLine(bodyRow, housingFinance);
        }
    }

    private void createFundWithLine(final List<String> bodyRow, final HousingFinance housingFinance) {
        for (int i = START_INSTITUTION_INDEX; i < bodyRow.size(); i++) {
            Long institutionId = (long) (i - 1);
            Institution institution = institutionService.findById(institutionId);
            int amount = integerConverter.convert(bodyRow.get(i));
            fundService.save(housingFinance, institution, amount);
        }
    }
}
