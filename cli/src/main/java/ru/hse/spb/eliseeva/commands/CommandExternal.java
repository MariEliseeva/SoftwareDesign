package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Class to run some external commands which are not described in the interpreter.
 */
public class CommandExternal implements Command {
    private String command;

    CommandExternal(String commandName, List<String> commandArguments){
        StringBuilder string = new StringBuilder(commandName);
        string.append(' ');
        for (String s : commandArguments) {
            string.append(s).append(' ');
        }
        command = string.toString();
    }

    /**
     * Runs the program if it exists and writes its output.
     * @param environment environment to take variables, write output etc.
     */
    @Override
    public void run(Environment environment){
        String s;
        try {
            Process process = Runtime.getRuntime().exec(command);
            BufferedReader programOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder result = new StringBuilder();
            while ((s = programOutput.readLine()) != null) {
                result.append(s).append(System.lineSeparator());
            }
            environment.writeToPipe(result.toString());
        }
        catch (IOException e) {
            environment.writeToErrors(e.getMessage() + System.lineSeparator());
        }
    }
}
