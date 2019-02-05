package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.parser.Executable;

import java.util.Collections;

import static org.junit.Assert.*;

public class CommandExternalTest {

    @Test
    public void run() throws LexerException {
        Environment environment = new Environment();
        assertFalse(environment.isEnd());
        CommandCreator.create("ls", Collections.singletonList("src/test/resources/"),
                Executable.getEmptyCommandExecutable()).run(environment);
        assertEquals("file1", environment.getOutput());
    }
}