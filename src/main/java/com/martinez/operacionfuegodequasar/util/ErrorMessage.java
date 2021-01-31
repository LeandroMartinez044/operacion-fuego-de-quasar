package com.martinez.operacionfuegodequasar.util;

public class ErrorMessage {

    private int code;

    private String description;

    public ErrorMessage(int code, String description){
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
