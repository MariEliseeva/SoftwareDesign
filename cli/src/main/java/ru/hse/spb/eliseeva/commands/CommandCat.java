package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.parser.Executable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

/**
 * Command cat: takes files from the given args or read from the input and writes in the output.
 */
public class CommandCat implements Command {
    private Executable previousCommand;
    private List<String> arguments;

    /**
     * Creates Cat command and remembers its arguments and the previous command
     * (in case arguments are empty and we need to take the result from pipe).
     * @param arguments files' names to write.
     * @param previousCommand command which was in the pipeline before cat.
     */
    CommandCat(List<String> arguments, Executable previousCommand) {
        this.arguments = arguments;
        this.previousCommand = previousCommand;
    }

    /**
     * If no arguments just runs the previous command and don't change its result.
     * Otherwise takes all files from arguments and writes their content.
     * @param environment environment to take variables, write output etc.
     */
    @Override
    public void run(Environment environment) throws LexerException {
        if (arguments.size() == 0) {
            previousCommand.execute(environment);
            return;
        }
        StringBuilder result = new StringBuilder();
        Scanner scanner;
        for (String fileName : arguments) {
            try {
                scanner = new Scanner(new FileInputStream(fileName));
            } catch (FileNotFoundException e) {
                result.append("cat: ").append(fileName).append(": No such file found.");
                continue;
            }
            while (scanner.hasNext()) {
                result.append(scanner.nextLine()).append("\n");
            }
        }
        environment.writeToPipe(result.toString().substring(0, result.length() - 1));
    }
}
