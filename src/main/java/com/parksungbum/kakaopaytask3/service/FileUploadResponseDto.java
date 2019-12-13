package com.parksungbum.kakaopaytask3.service;

public class FileUploadResponseDto {
    private String fileName;
    private String contentType;
    private long size;

    public FileUploadResponseDto(String fileName, String contentType, long size) {
        this.fileName = fileName;
        this.contentType = contentType;
        this.size = size;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public long getSize() {
        return size;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
