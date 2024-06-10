package com.beautysalon.core.utils;

import java.nio.file.Path;

public final class PathUtils {

    public static String normalizeForUrl(String path) {
        return path.replaceAll("[/\\\\]", "+");
    }

    public static String originalPath(String path) {
        return path.replaceAll("\\+", "/");
    }

    public static String[] splitPathFilename(String fullName) {
        Path path = Path.of(fullName);
        Path parent = path.getParent();
        String pathFile = (parent != null) ? parent.toString() : "";
        String filename = path.toFile().getName();
        pathFile = pathFile.replaceAll("[\\\\]", "/");
        return new String[] {pathFile, filename};
    }
    
    public static String[] splitNormalizedFilename(String filename) {
        return filename.split("\\+");
    }

    public static String parseEmployeeImagePath(String username, String filename) {
        return filename != null
                ? String.format("emp/%s/%s", username, filename)
                : String.format("emp/%s", username);
    }

    public static String parsePreviewImageFilename(String filename) {
        String[] s = filename.split("\\.");
        return (s.length > 0)
                ? "preview"
                : String.format("%s.%s", "preview", s[s.length-1]);
    }
}
