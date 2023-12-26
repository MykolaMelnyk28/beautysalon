package com.beautysalon.api.v1.dto;

public class ImageDto {
    private String url;
    private String fullName;
    private String name;
    private String path;

    public ImageDto() {
    }

    public ImageDto(String url, String fullName, String name, String path) {
        this.url = url;
        this.fullName = fullName;
        this.name = name;
        this.path = path;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


}
