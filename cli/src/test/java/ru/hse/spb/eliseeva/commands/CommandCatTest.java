package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.parser.Executable;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class CommandCatTest {

    @Test
    public void run() throws LexerException {
        Environment environment = new Environment();
        CommandCreator.create("cat", Collections.singletonList("src/test/resources/file1"),
                Executable.getEmptyCommandExecutable()).run(environment);
        assertEquals("1 2 3 4    5", environment.getOutput());
    }

    @Test
    public void runPipe() throws LexerException {
        Environment environment = new Environment();
        environment.writeToPipe("1 2");
        CommandCreator.create("cat", new ArrayList<>(), Executable.getEmptyCommandExecutable()).run(environment);
        assertEquals("1 2", environment.getOutput());
    }
}