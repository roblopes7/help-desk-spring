package com.udemy.helpdesk.services.exceptions;

public class ObjectNotFounException extends RuntimeException{

    public ObjectNotFounException(String message) {
        super(message);
    }

    public ObjectNotFounException(String message, Throwable cause) {
        super(message, cause);
    }
}
