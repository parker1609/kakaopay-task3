package com.parksungbum.kakaopaytask3.controller;

import com.parksungbum.kakaopaytask3.service.CsvFileUploadService;
import com.parksungbum.kakaopaytask3.service.FileUploadResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class CsvFileUploadController {
    private final CsvFileUploadService csvFileUploadService;

    public CsvFileUploadController(final CsvFileUploadService csvFileUploadService) {
        this.csvFileUploadService = csvFileUploadService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponseDto> upload(@RequestParam("file") MultipartFile multipartFile) {
        FileUploadResponseDto fileUploadResponseDto = csvFileUploadService.save(multipartFile);
        return ResponseEntity.ok(fileUploadResponseDto);
    }
}
