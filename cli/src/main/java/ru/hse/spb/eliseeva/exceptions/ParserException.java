package ru.hse.spb.eliseeva.exceptions;

/**
 * Thrown when wrong pipe usage found or some other parser problems.
 */
public class ParserException extends Exception {
    public ParserException(String message){
        super(message);
    }
}
