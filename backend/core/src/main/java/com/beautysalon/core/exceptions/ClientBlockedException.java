package com.beautysalon.core.exceptions;

import com.beautysalon.core.entities.Client;

public class ClientBlockedException extends RuntimeException {

    private Client client;

    public ClientBlockedException(Client client) {
        this.client = client;
    }

    public ClientBlockedException(String message, Client client) {
        super(message);
        this.client = client;
    }

    public ClientBlockedException(String message, Throwable cause, Client client) {
        super(message, cause);
        this.client = client;
    }

    public ClientBlockedException(Throwable cause, Client client) {
        super(cause);
        this.client = client;
    }

    public ClientBlockedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Client client) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
