package com.beautysalon.api.v1.exceptions;

import com.beautysalon.api.v1.exceptions.StorageException;

public class StorageFileNotFoundException extends StorageException {
    public StorageFileNotFoundException(String message) {
        super(message);
    }

    public StorageFileNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
