package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Command that writes line number, word number and byte number for all the given files
 * of for input if no arguments given.
 */
public class CommandWc implements Command {
    private List<String> arguments;

    CommandWc(List<String> arguments) {
        this.arguments = arguments;
    }

    private int totalLines;
    private int totalWords;
    private int totalBytes;

    /**
     * If no files given count data in the input. Otherwise look at all the given files, runs cat command to get their
     * content and counts needed statistics.
     * @param environment environment to take variables, write output etc.
     */
    @Override
    public void run(Environment environment) {
        totalWords = 0;
        totalLines = 0;
        totalBytes = 0;

        if (arguments.size() == 0) {
            countFromInput(environment);
            return;
        }
        StringBuilder result = new StringBuilder();
        for (String fileName : arguments) {
            String fileContent = "";
            try {
                fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
            } catch (IOException e) {
                environment.writeToErrors("wc: " + fileName + ": No such file found." + System.lineSeparator());
            }
            result.append(addStatistics(fileContent)).append(" ").append(fileName).append(System.lineSeparator());
        }
        if (arguments.size() > 1) {
            result.append(getTotalInformation());
        }
        result.append(System.lineSeparator());
        environment.writeToPipe(result.toString());
    }

    private String getTotalInformation() {
        return totalLines + " " + totalWords + " " + totalBytes + " total" + System.lineSeparator();
    }
    private void countFromInput(Environment environment) {
        if (environment.hasOutPut()) {
            String previousResult = environment.getOutput();
            environment.writeToPipe(addStatistics(previousResult) + System.lineSeparator());
        } else {
            environment.writeToPipe("0 0 0" + System.lineSeparator());
        }
    }

    private String addStatistics(String s) {
        int linesNumber = s.length() - s.replace(System.lineSeparator(), "").length();
        int wordsNumber = s.trim().isEmpty()? 0 : s.trim().split("\\s+").length;
        int bytesNumber = s.getBytes().length;
        totalLines += linesNumber;
        totalWords += wordsNumber;
        totalBytes += bytesNumber;
        return linesNumber + " " + wordsNumber + " " + bytesNumber;
    }
}
