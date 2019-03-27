package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

import java.nio.file.Paths;

/**
 * Command to print the current working directory.
 */
public class CommandPwd implements Command {
     CommandPwd(){}
    /**
     * Prints the current working directory.
     */
    @Override
    public void run(Environment environment) {
        environment.writeToPipe(Paths.get("").toAbsolutePath().toString());
    }
}
