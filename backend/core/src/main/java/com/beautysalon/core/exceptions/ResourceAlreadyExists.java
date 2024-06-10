package com.beautysalon.core.exceptions;

public class ResourceAlreadyExists extends RuntimeException {
    public ResourceAlreadyExists(String message) {
        super(message);
    }

    public ResourceAlreadyExists(Throwable cause) {
        super(cause);
    }
}
