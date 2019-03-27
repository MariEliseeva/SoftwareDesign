package ru.hse.spb.eliseeva.commands;
import picocli.CommandLine;
import ru.hse.spb.eliseeva.Environment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CommandGrep implements Command {

    private List<String> arguments;

    @CommandLine.Option(names = {"-i"}, description = "Perform case insensitive matching.")
    private boolean ignoreCase = false;

    @CommandLine.Option(names = {"-w"}, description = "The expression is searched for as a word.")
    private boolean wordRegexp = false;

    @CommandLine.Option(names = {"-A"}, paramLabel = "-A", description = "Print NUM lines of trailing context after each match.")
    private int afterContext = 0;

    @CommandLine.Parameters(arity = "1", index = "0", paramLabel = "REGEX", description = "Patten to match.")
    private String regex;

    @CommandLine.Parameters(arity = "0..*", index = "1..*", paramLabel = "FILE", description = "File(s) to search.")
    private String[] files = new String[0];

    CommandGrep(List<String> arguments) {
        this.arguments = arguments;
    }

    @Override
    public void run(Environment environment) {
        try {
            CommandLine.populateCommand(this, arguments.toArray(new String[0]));
        } catch (CommandLine.ParameterException e) {
            if (e.getArgSpec() != null) {
                environment.writeToErrors("grep: Invalid argument" + System.lineSeparator());
            } else {
                environment.writeToErrors("grep: Invalid option " + e.getCommandLine().getUnmatchedArguments().get(0)
                        + System.lineSeparator());
            }
            return;
        }
        if (afterContext < 0) {
            environment.writeToErrors("grep: Invalid argument" + System.lineSeparator());
            return;
        }
        if (wordRegexp) {
            regex = "\\b" + regex + "\\b";
        }
        Pattern pattern = Pattern.compile(regex, ignoreCase ? Pattern.CASE_INSENSITIVE : 0);
        int linesToPrintAfter = 0;
        StringBuilder result = new StringBuilder();

        if (files.length == 0) {
            String[] expresion = environment.getOutput().split(System.lineSeparator());
            for (int i = 0; i < expresion.length; i++) {
                String line = expresion[i];
                if (pattern.matcher(line).find()) {
                    result.append(line).append(System.lineSeparator());
                    linesToPrintAfter = afterContext;
                } else if (linesToPrintAfter > 0) {
                    linesToPrintAfter--;
                    result.append(line).append(System.lineSeparator());
                    if (linesToPrintAfter == 0 && i != expresion.length - 1) {
                        result.append("--").append(System.lineSeparator());
                    }
                }
            }
        } else {
            Scanner scanner;
            for (String fileName : files) {
                try {
                    scanner = new Scanner(new FileInputStream(fileName));
                } catch (FileNotFoundException e) {
                    environment.writeToErrors("grep: " + fileName + ": No such file found." + System.lineSeparator());
                    continue;
                }
                while (scanner.hasNext()) {
                    String line = scanner.nextLine();
                    if (pattern.matcher(line).find()) {
                        result.append(line).append(System.lineSeparator());
                        linesToPrintAfter = afterContext;
                    } else if (linesToPrintAfter > 0) {
                        linesToPrintAfter--;
                        result.append(line).append(System.lineSeparator());
                        if (linesToPrintAfter == 0 && scanner.hasNext()) {
                            result.append("--").append(System.lineSeparator());
                        }
                    }
                }
            }
        }
        if (result.length() > 0) {
            environment.writeToPipe(result.toString());
        }
    }
}
