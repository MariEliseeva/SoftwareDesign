package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.parser.Executable;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CommandEchoTest {
    @Test
    public void run() throws LexerException {
        Environment environment = new Environment();
        CommandCreator.create("echo", Arrays.asList("arb", "hale"),
                Executable.getEmptyCommandExecutable()).run(environment);
        assertEquals("arb hale", environment.getOutput());
    }
}