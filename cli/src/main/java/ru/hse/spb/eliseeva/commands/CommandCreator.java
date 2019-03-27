package ru.hse.spb.eliseeva.commands;

import java.util.List;

/**
 * Returns a class for a command by its name.
 */
public class CommandCreator {
    /**
     * Creates new command class.
     * @param commandName name of the command
     * @param commandArguments arguments
     * @return class representing command with the given name and arguments.
     */
    public static Command create(String commandName, List<String> commandArguments) {
        switch (commandName) {
            case "cat":
                return new CommandCat(commandArguments);
            case "echo":
                return new CommandEcho(commandArguments);
            case "wc":
                return new CommandWc(commandArguments);
            case "pwd":
                return new CommandPwd();
            case "exit":
                return new CommandExit();
            case "=":
                return new CommandsAssignment(commandArguments);
            default:
                return new CommandExternal(commandName, commandArguments);
        }
    }
}
