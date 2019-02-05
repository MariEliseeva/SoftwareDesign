package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

import java.util.List;

/**
 * Command to write the given arguments.
 */
public class CommandEcho implements Command {
    private List<String> arguments;

    CommandEcho(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public void run(Environment environment) {
        StringBuilder result = new StringBuilder();
        for (String argument : arguments) {
            result.append(argument).append(" ");
        }
        environment.writeToPipe(result.toString().substring(0, result.toString().length() - 1));
    }
}
