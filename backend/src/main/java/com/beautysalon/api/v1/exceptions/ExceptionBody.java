package com.beautysalon.api.v1.exceptions;

public class ExceptionBody {
    private String message;
    private String[] errors;

    public ExceptionBody(String message) {
        this(message, new String[0]);
    }

    public ExceptionBody(String message, String[] errors) {
        this.message = message;
        this.errors = errors;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String[] getErrors() {
        return errors;
    }

    public void setErrors(String[] errors) {
        this.errors = errors;
    }
}
