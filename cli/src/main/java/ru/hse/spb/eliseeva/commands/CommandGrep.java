package ru.hse.spb.eliseeva.commands;
import picocli.CommandLine;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.parser.Executable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class CommandGrep implements Command {
    private Executable previousCommand;

    @CommandLine.Option(names = {"-i"}, description = "Perform case insensitive matching.")
    private boolean ignoreCase = false;

    @CommandLine.Option(names = {"-w"}, description = "The expression is searched for as a word.")
    private boolean wordRegexp = false;

    @CommandLine.Option(names = {"-A"}, paramLabel = "NUM", description = "Print NUM lines of trailing context after each match.")
    private int afterContext = 0;

    @CommandLine.Parameters(arity = "1", index = "0", paramLabel = "REGEX", description = "Patten to match.")
    private String regex;

    @CommandLine.Parameters(arity = "0..*", index = "1..*", paramLabel = "FILE", description = "File(s) to search.")
    private String[] files = new String[0];

    CommandGrep(List<String> arguments, Executable previousCommand) {
        this.previousCommand = previousCommand;
        CommandLine.populateCommand(this, arguments.toArray(new String[arguments.size()]));
    }

    @Override
    public void run(Environment environment) throws LexerException {
        if (files.length == 0) {
            previousCommand.execute(environment);
            files = new String[1];
            files[0] = environment.getOutput();
            return;
        }

        Pattern pattern = Pattern.compile(regex, ignoreCase ? Pattern.CASE_INSENSITIVE : 0);
        int linesToPrintAfter = 0;
        StringBuilder result = new StringBuilder();
        Scanner scanner;
        for (String fileName : files) {
            try {
                scanner = new Scanner(new FileInputStream(fileName));
            } catch (FileNotFoundException e) {
                result.append("grep: ").append(fileName).append(": No such file found.");
                continue;
            }
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                boolean matches = false;
                if (wordRegexp) {
                    for (String word : line.trim().split("\\s+")) {
                        if (pattern.matcher(word).matches()) {
                            matches = true;
                            break;
                        }
                    }
                } else {
                    matches = pattern.matcher(line).find();
                }
                if (matches) {
                    result.append(line).append("\n");
                    linesToPrintAfter = afterContext;
                } else if (linesToPrintAfter > 0) {
                    linesToPrintAfter--;
                    result.append(line).append("\n");
                    if (linesToPrintAfter == 0 && scanner.hasNext()) {
                        result.append("--\n");
                    }
                }
            }
        }
        if (result.length() > 0) {
            environment.writeToPipe(result.toString().substring(0, result.length() - 1));
        }
    }
}
