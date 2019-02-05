package ru.hse.spb.eliseeva.commands;


import ru.hse.spb.eliseeva.parser.Executable;

import java.util.List;

public class CommandCreator {
    public static Command create(String commandName, List<String> commandArguments, Executable previousCommand) {
        switch (commandName) {
            case "cat":
                return new CommandCat(commandArguments, previousCommand);
            case "echo":
                return new CommandEcho(commandArguments);
            case "wc":
                return new CommandWc(commandArguments, previousCommand);
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
