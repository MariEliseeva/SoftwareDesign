package ru.hse.spb.eliseeva.parser;

import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.commands.CommandCreator;
import ru.hse.spb.eliseeva.exceptions.LexerException;
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
    public Executable(List<Word> commandParts) {
        commandName = commandParts.get(0);
        commandArguments = commandParts.subList(1, commandParts.size());
    }

    public String getCommandName(Environment environment) throws LexerException {
        return commandName.evaluate(environment);
    }

    public List<String> getCommandArguments(Environment environment) throws LexerException {
        List<String> evaluatedArguments = new ArrayList<>();
        for (Word argument : commandArguments) {
            evaluatedArguments.add(argument.evaluate(environment));
        }
        return evaluatedArguments;
    }
}
