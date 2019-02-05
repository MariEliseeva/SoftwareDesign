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
    private Executable previousCommand;

    /**
     * Creates CommandExecutable from information about current and about previous command.
     * @param commandParts name and arguments of the current command.
     * @param previousCommand information about previous command.
     */
    public Executable(List<Word> commandParts, Executable previousCommand) {
        commandName = getCommandName(commandParts);
        commandArguments = getCommandArguments(commandParts);
        this.previousCommand = previousCommand;
    }

    private boolean isEmpty = false;
    private Executable(){
        isEmpty = true;
    }

    /**
     * Used for the first command on the pipeline, because it does not have a previous one.
     * @return empty CommandExecutable
     */
    public static Executable getEmptyCommandExecutable() {
        return new Executable();
    }

    /**
     * Executes the command in stores: evaluate name, arguments, creates needed class according to the evaluated name
     * and runs created command.
     * @param environment environment to run the command with.
     */
    public void execute(Environment environment) throws LexerException {
        if (isEmpty) {
            return;
        }
        CommandCreator.create(commandName.evaluate(environment), evaluateArguments(environment),
                previousCommand).run(environment);
    }

    Word getCommandName() {
        return commandName;
    }

    Executable getPreviousCommand() {
        return previousCommand;
    }

    private Word getCommandName(List<Word> commandParts) {
        return commandParts.get(0);
    }

    private static List<Word> getCommandArguments(List<Word> commandParts) {
        return commandParts.subList(1, commandParts.size());
    }

    private List<String> evaluateArguments(Environment environment) throws LexerException {
        List<String> evaluatedArguments = new ArrayList<>();
        for (Word argument : commandArguments) {
            evaluatedArguments.add(argument.evaluate(environment));
        }
        return evaluatedArguments;
    }
}
