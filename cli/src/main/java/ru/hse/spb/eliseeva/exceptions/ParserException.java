package ru.hse.spb.eliseeva.exceptions;

public class ParserException extends Exception {
    private String message;
    public ParserException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
