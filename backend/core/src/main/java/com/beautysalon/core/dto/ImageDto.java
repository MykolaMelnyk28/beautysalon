package com.beautysalon.core.dto;

import com.beautysalon.core.dto.validation.OnCreate;
import com.beautysalon.core.dto.validation.OnPatch;
import com.beautysalon.core.dto.validation.OnPut;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public class ImageDto {
    @NotBlank(message = "must not be empty",
        groups = {OnCreate.class, OnPut.class, OnPatch.class})
    private String url;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    @Length(max = 330, message = "must be 330 characters or less.",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    private String fullName;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    @Length(max = 80, message = "text must be 80 characters or less.",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    private String name;

    @NotBlank(message = "must not be empty",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    @Length(max = 255, message = "text must be 255 characters or less.",
            groups = {OnCreate.class, OnPut.class, OnPatch.class})
    private String path;


    private boolean isPreviewImage;

    public ImageDto() {
    }

    public ImageDto(String url, String fullName, String name, String path, boolean isPreviewImage) {
        this.url = url;
        this.fullName = fullName;
        this.name = name;
        this.path = path;
        this.isPreviewImage = isPreviewImage;
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

    public boolean isPreviewImage() {
        return isPreviewImage;
    }

    public void setPreviewImage(boolean previewImage) {
        isPreviewImage = previewImage;
    }
}
