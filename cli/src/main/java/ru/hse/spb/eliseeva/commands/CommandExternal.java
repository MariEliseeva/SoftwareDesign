package ru.hse.spb.eliseeva.commands;

import ru.hse.spb.eliseeva.Environment;

import java.io.*;
import java.util.List;

/**
 * Class to run some external commands which are not described in the interpreter.
 */
public class CommandExternal implements Command {
    private String command;
    private String name;

    CommandExternal(String commandName, List<String> commandArguments){
        name = commandName;
        command = commandName + " " + String.join(" ", commandArguments);
    }

    /**
     * Runs the program if it exists and writes its output.
     * @param environment environment to write output and errors.
     */
    @Override
    public void run(Environment environment){
        try {
            Process process = Runtime.getRuntime().exec(command);
            environment.writeToPipe(read(process.getInputStream()));
            environment.writeToErrors(read(process.getErrorStream()));
            process.waitFor();
        }
        catch (IOException | InterruptedException e) {
            environment.writeToErrors(e.getMessage() + System.lineSeparator());
        }
    }

    @Override
    public String getName() {
        return name;
    }

    private String read(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder result = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            result.append(line).append(System.lineSeparator());
        }
        reader.close();
        return result.toString();
    }
}
