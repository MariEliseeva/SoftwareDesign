package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.parser.Executable;

import java.io.File;
import java.util.Collections;
import java.util.List;

/**
 * Command that writes line number, word number and byte number for all the given files
 * of for input if no arguments given.
 */
public class CommandWc implements Command {
    private List<String> arguments;
    private Executable previousCommand;

    CommandWc(List<String> arguments, Executable previousCommand) {
        this.arguments = arguments;
        this.previousCommand = previousCommand;
    }

    private int totalLines = 0;
    private int totalWords = 0;
    private int totalBytes = 0;

    /**
     * If no files given count data in the input. Otherwise look at all the given files, runs cat command to get their
     * content and counts needed statistics.
     * @param environment environment to take variables, write output etc.
     */
    @Override
    public void run(Environment environment) throws LexerException {
        if (arguments.size() == 0) {
            countFromInput(environment);
            return;
        }
        StringBuilder result = new StringBuilder();
        for (String argument : arguments) {
            if (isFileExists(argument)) {
                String catResult = runCatCommand(environment, argument);
                result.append(addStatistics(catResult)).append(" ").append(argument).append("\n");
            }
        }
        if (arguments.size() > 1) {
            result.append(getTotalInformation());
        }
        environment.writeToPipe(result.toString());
    }

    private String getTotalInformation() {
        return totalLines + " " + totalWords + " " + totalBytes + " total\n";
    }

    private boolean isFileExists(String fileName) {
        return new File(fileName).exists() && !new File(fileName).isDirectory();
    }

    private void countFromInput(Environment environment) throws LexerException {
        previousCommand.execute(environment);
        String previousResult = environment.getOutput();
        environment.writeToPipe(addStatistics(previousResult));
    }

    private String runCatCommand(Environment environment, String argument) throws LexerException {
        CommandCreator.create("cat", Collections.singletonList(argument), previousCommand).run(environment);
        return environment.getOutput();
    }


    private String addStatistics(String s) {
        int linesNumber = s.split(System.lineSeparator()).length;
        int wordsNumber = s.trim().split("\\s+").length;
        int bytesNumber = s.getBytes().length;
        totalLines += linesNumber;
        totalWords += wordsNumber;
        totalBytes += bytesNumber;
        return linesNumber + " " + wordsNumber + " " + bytesNumber;
    }
}
