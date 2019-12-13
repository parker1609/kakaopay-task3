package com.parksungbum.kakaopaytask3.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvFileUploadService {

    public FileUploadResponseDto save(MultipartFile file) {
        System.out.println(file.getSize());
        return new FileUploadResponseDto(
                file.getOriginalFilename(),
                file.getContentType(),
                file.getSize());
    }
}
