package com.beautysalon.api.v1.exceptions;

public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists(String message) {
        super(message);
    }

    public ResourceAlreadyExists(Throwable cause) {
        super(cause);
    }
}
