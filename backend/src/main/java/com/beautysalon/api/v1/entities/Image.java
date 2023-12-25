package com.beautysalon.api.v1.entities;

import jakarta.persistence.*;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "images", uniqueConstraints = @UniqueConstraint(
        columnNames = {"path", "filename"}
))
public class Image {

    public static class Builder {
        private UUID id;
        private String path = "/";
        private String filename;
        private byte[] bytes;

        Builder() {
            this.id = UUID.randomUUID();
            this.path = "/";
            this.filename = id.toString();
            this.bytes = new byte[0];
        }

        public Builder id(UUID id) {
            this.id = id;
            return this;
        }

        public Builder path(String path) {
            this.path = path;
            return this;
        }

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder bytes(byte[] bytes) {
            this.bytes = bytes;
            return this;
        }

        public Builder setFullPath(String fullPath) {
            int lastSep = fullPath.lastIndexOf("/");
            this.path = fullPath.substring(0, lastSep-1);
            this.filename = fullPath.substring(lastSep+1, fullPath.length()-1);
            return this;
        }

        public Image build() {
            return new Image(id, path, filename, bytes);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(nullable = false)
    private String path = "/";
    @Column(nullable = false)
    private String filename;

    @Lob
    @Column(nullable = false)
    private byte[] bytes;

    public Image() {

    }

    public Image(UUID id, String path, String filename, byte[] bytes) {
        this.id = id;
        this.path = path;
        this.filename = filename;
        this.bytes = bytes;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getFullName() {
        return String.join("/", path, filename);
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Image image = (Image) o;
        return Objects.equals(id, image.id) && Objects.equals(path, image.path) && Objects.equals(filename, image.filename) && Arrays.equals(bytes, image.bytes);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, path, filename);
        result = 31 * result + Arrays.hashCode(bytes);
        return result;
    }
}