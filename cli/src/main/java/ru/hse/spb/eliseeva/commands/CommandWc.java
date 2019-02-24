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

    /**
     * If no files given counts data in the input. Otherwise looks at all given files, reads their
     * content and counts needed statistics using an inner class to collect is.
     * @param environment environment to write output and errors, get previous command result.
     */
    @Override
    public void run(Environment environment) {
        Information information = new Information();
        String result;
        if (arguments.size() == 0) {
            result = countFromInput(environment, information);
        } else {
            result = countFromFile(environment, information);
        }
        environment.writeToPipe(result);
    }

    @Override
    public String getName() {
        return "wc";
    }

    private String countFromInput(Environment environment, Information information) {
        if (environment.hasOutPut()) {
            String previousResult = environment.getOutput();
            return information.addStatistics(previousResult) + System.lineSeparator();
        } else {
            return "0 0 0" + System.lineSeparator();
        }
    }

    private String countFromFile(Environment environment, Information information) {
        StringBuilder result = new StringBuilder();
        for (String fileName : arguments) {
            String fileContent;
            try {
                fileContent = new String(Files.readAllBytes(Paths.get(fileName)));
                result.append(information.addStatistics(fileContent)).append(" ").append(fileName).append(System.lineSeparator());
            } catch (IOException e) {
                environment.writeToErrors("wc: " + fileName + ": No such file found." + System.lineSeparator());
            }
        }
        if (arguments.size() > 1) {
            result.append(information.getTotalInformation());
        }
        return result.toString();
    }

    private class Information {
        private int totalWords = 0;
        private int totalLines = 0;
        private int totalBytes = 0;

        private String getTotalInformation() {
            return totalLines + " " + totalWords + " " + totalBytes + " total" + System.lineSeparator();
        }

        private String addStatistics(String s) {
            int linesNumber = s.contains(System.lineSeparator()) ?
                    (s.length() - s.replace(System.lineSeparator(), "").length())
                    / System.lineSeparator().length() : 1;
            int wordsNumber = s.trim().isEmpty()? 0 : s.trim().split("\\s+").length;
            int bytesNumber = s.getBytes().length;
            totalLines += linesNumber;
            totalWords += wordsNumber;
            totalBytes += bytesNumber;
            return linesNumber + " " + wordsNumber + " " + bytesNumber;
        }
    }
}
