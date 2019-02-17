package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Command cat: takes files from the given args or read from the input and writes in the output.
 */
public class CommandCat implements Command {
    private List<String> arguments;

    /**
     * Creates Cat command and remembers its arguments and the previous command
     * (in case arguments are empty and we need to take the result from pipe).
     * @param arguments files' names to write.
     */
    CommandCat(List<String> arguments) {
        this.arguments = arguments;
    }

    /**
     * If no arguments just runs the previous command and don't change its result.
     * Otherwise takes all files from arguments and writes their content.
     * @param environment environment to take variables, write output etc.
     */
    @Override
    public void run(Environment environment) {
        if (arguments.size() == 0) {
            return;
        }
        StringBuilder result = new StringBuilder();
        for (String fileName : arguments) {
            try {
                result.append(new String(Files.readAllBytes(Paths.get(fileName))));
            } catch (IOException e) {
                environment.writeToErrors("cat: " + fileName + ": No such file found." + System.lineSeparator());
            }
        }
        if (result.length() != 0) {
            environment.writeToPipe(result.toString());
        }
    }
}
