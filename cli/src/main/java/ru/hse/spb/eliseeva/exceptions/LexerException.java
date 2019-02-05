package ru.hse.spb.eliseeva.exceptions;

public class LexerException extends Exception {
    private String message;
    public LexerException(String message){
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
