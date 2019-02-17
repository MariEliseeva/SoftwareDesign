package ru.hse.spb.eliseeva.parser;

import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;
import ru.hse.spb.eliseeva.substitution.Word;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to collect the information about command without substitution or any operations in name or arguments.
 */
public class Executable {
    private Word commandName;
    private List<Word> commandArguments;

    /**
     * Creates CommandExecutable from information about current and about previous command.
     * @param commandParts name and arguments of the current command.
     */
    Executable(List<Word> commandParts) {
        commandName = commandParts.get(0);
        commandArguments = commandParts.subList(1, commandParts.size());
    }

    /**
     * Evaluate the command name using the given environment
     * @param environment environment to take values
     * @return evaluated command name
     * @throws EnvironmentException if no such variable found
     */
    public String getCommandName(Environment environment) throws EnvironmentException {
        return commandName.evaluate(environment);
    }

    /**
     * Evaluate the command name using the given environment
     * @param environment environment to take values
     * @return evaluated command name
     * @throws EnvironmentException if no such variable found
     */
    public List<String> getCommandArguments(Environment environment) throws EnvironmentException {
        List<String> evaluatedArguments = new ArrayList<>();
        for (Word argument : commandArguments) {
            evaluatedArguments.add(argument.evaluate(environment));
        }
        return evaluatedArguments;
    }
}
