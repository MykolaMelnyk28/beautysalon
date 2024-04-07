package com.beautysalon.api.v1.entities;

import jakarta.persistence.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "images", uniqueConstraints = @UniqueConstraint(
        columnNames = { "user_id", "isPreviewImage" }
))
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name="path", nullable = false)
    private String path;
    @Column(name="fullName", nullable = false, unique = true)
    private String fullName;
    @Column(name = "originalFileName", nullable = false)
    private String originalFileName;
    @Column(name = "size", nullable = false)
    private Long size;
    @Column(name = "contentType", nullable = false)
    private String contentType;
    @Column(name = "isPreviewImage", nullable = false)
    private boolean isPreviewImage = false;

    @Column(name = "bytes", nullable = false)
    private byte[] bytes;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public Image() {
    }

    public Image(Long id, String name, String path, String fullName, String originalFileName, Long size, String contentType, boolean isPreviewImage, byte[] bytes, UserEntity user) {
        this.id = id;
        this.name = name;
        this.path = path;
        this.fullName = fullName;
        this.originalFileName = originalFileName;
        this.size = size;
        this.contentType = contentType;
        this.isPreviewImage = isPreviewImage;
        this.bytes = bytes;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public boolean isPreviewImage() {
        return isPreviewImage;
    }

    public void setPreviewImage(boolean previewImage) {
        isPreviewImage = previewImage;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return isPreviewImage == image.isPreviewImage && Objects.equals(id, image.id) && Objects.equals(name, image.name) && Objects.equals(path, image.path) && Objects.equals(fullName, image.fullName) && Objects.equals(originalFileName, image.originalFileName) && Objects.equals(size, image.size) && Objects.equals(contentType, image.contentType) && Arrays.equals(bytes, image.bytes) && Objects.equals(user, image.user);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, path, fullName, originalFileName, size, contentType, isPreviewImage, user);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}