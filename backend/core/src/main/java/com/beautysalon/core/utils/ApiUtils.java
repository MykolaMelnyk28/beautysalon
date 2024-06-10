package com.beautysalon.core.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

public final class ApiUtils {
    public static final String BASE_URL = "http://localhost:8080/api/v1";

    public static String parseUrlFromBase(String...nodes) {
        if (nodes.length == 0)
            return BASE_URL;
        return BASE_URL + "/" + Arrays.stream(nodes)
                .map(PathUtils::normalizeForUrl)
                .collect(Collectors.joining("/"));
    }

    private ApiUtils() {}
}
