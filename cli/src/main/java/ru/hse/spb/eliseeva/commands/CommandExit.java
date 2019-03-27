package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

/**
 * Command to finish interpretation.
 */
public class CommandExit implements Command {
    CommandExit(){}
    /**
     * Marks in the environment that we should end the process.
     * @param environment environment to mark the process is ended.
     */
    @Override
    public void run(Environment environment) {
        environment.end();
    }
}
