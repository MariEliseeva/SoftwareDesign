package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.Environment;

/**
 * Interface of some command we can run using the given environment.
 */
public interface Command {
    /**
     * Runs the command using the given environment.
     * @param environment environment to take variables, write output etc.
     */
    void run(Environment environment) throws LexerException;
}
