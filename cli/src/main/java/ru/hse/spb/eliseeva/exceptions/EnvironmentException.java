package ru.hse.spb.eliseeva.exceptions;

/**
 * Thrown when no variable found or some other problems connected with the environment exist.
 */
public class EnvironmentException extends Exception {
    public EnvironmentException(String message) {
        super(message);
    }
}
