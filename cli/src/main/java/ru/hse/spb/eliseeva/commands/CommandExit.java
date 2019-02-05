package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

/**
 * Command to finish interpretation.
 */
public class CommandExit implements Command {
    /**
     * Marks in the environment that we should end the process.
     * @param environment environment to take variables, write output etc.
     */
    @Override
    public void run(Environment environment) {
        environment.end();
    }
}
