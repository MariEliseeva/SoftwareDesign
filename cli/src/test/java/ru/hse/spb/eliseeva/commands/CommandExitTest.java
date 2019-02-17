package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.parser.Executable;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommandExitTest {

    @Test
    public void run() throws LexerException {
        Environment environment = new Environment();
        assertFalse(environment.isEnd());
        CommandCreator.create("exit", new ArrayList<>()).run(environment);
        assertTrue(environment.isEnd());
    }
}