package com.beautysalon.api.v1.utils;

import java.nio.file.Path;

public final class PathUtils {

    public static String normalizeUrlPath(String path) {
        return path.replaceAll("[/\\\\]", "+");
    }

    public static String[] splitPathFilename(Path path) {
        Path parent = path.getParent();
        String pathFile = (parent != null) ? parent.toString() : "";
        String filename = path.toFile().getName();
        return new String[] {pathFile, filename};
    }

}
