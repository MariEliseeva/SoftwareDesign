package ru.hse.spb.eliseeva.exceptions;

/**
 * Thrown when lexer has problems: no paired quote found or wrong variable name.
 */
public class LexerException extends Exception {
    public LexerException(String message){
            super(message);
    }
}
