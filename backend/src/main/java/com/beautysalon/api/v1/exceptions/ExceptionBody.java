package com.beautysalon.api.v1.exceptions;

import org.springframework.http.HttpStatus;

import java.util.*;

public class ExceptionBody {

    public static class Builder {
        private String message;
        private Map<String, List<String>> errors;

        public Builder() {
            this.message = "";
            this.errors = new HashMap<>();
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }
        public Builder appendError(String key, String value) {
            List<String> list = errors.get(key);
            if (list != null) {
                list.add(value);
            } else {
                errors.put(key, Collections.singletonList(value));
            }
            return this;
        }

        public Builder removeError(String key, String value) {
            List<String> list = errors.get(key);
            if (list != null) {
                if (value == null) {
                    errors.remove(key);
                } else {
                    list.remove(value);
                }
            }
            return this;
        }

        public Builder removeError(String key) {
            return removeError(key, null);
        }

        public ExceptionBody build() {
            return new ExceptionBody(message, errors);
        }
    }

    public static Builder builder() {
        return new Builder();
    }

    private final String message;
    private final Map<String, List<String>> errors;

    public ExceptionBody(String message) {
        this.message = message;
        this.errors = Map.of();
    }

    public ExceptionBody(String message, Map<String, List<String>> errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public Map<String, List<String>> getErrors() {
        return errors;
    }
}
