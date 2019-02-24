package ru.hse.spb.eliseeva.commands;

import java.util.List;

/**
 * Class to return a class for a command by its name.
 */
public class CommandCreator {
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
            case "grep":
                return new CommandGrep(commandArguments);
            case "=":
                return new CommandsAssignment(commandArguments);
            default:
                return new CommandExternal(commandName, commandArguments);
        }
    }
}
