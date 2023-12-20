package com.beautysalon.api.v1.dto;

import org.springframework.web.multipart.MultipartFile;

public class ImageDto {
    private MultipartFile file;
    private String path;
    private String filename;

    public ImageDto() {
    }

    public ImageDto(MultipartFile file, String path, String filename) {
        this.file = file;
        this.path = path;
        this.filename = filename;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }
}
