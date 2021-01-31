package com.martinez.operacionfuegodequasar.exceptions;

public class NotFoundMessageException extends RuntimeException {
    public NotFoundMessageException(String message){
        super(message);
    }
}
