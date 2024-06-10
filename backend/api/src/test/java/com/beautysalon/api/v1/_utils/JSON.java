package com.beautysalon.api.v1._utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public final class JSON {
    public static String stringify(Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
