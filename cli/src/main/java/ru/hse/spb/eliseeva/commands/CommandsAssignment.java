package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

import java.util.List;

/**
 * Assign the given variable with the given value.
 */
public class CommandsAssignment implements Command {
    private String leftArgument;
    private String rightArgument;

    CommandsAssignment(List<String> arguments) {
        leftArgument = arguments.get(0);
        rightArgument = arguments.get(1);
    }

    @Override
    public void run(Environment environment) {
        environment.setVariableValue(leftArgument, rightArgument);
    }
}
