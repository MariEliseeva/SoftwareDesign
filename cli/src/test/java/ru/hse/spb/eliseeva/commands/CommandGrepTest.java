package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.parser.Executable;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CommandGrepTest {

    @Test
    public void run() throws LexerException{
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("abc", "src/test/resources/file2"),
                Executable.getEmptyCommandExecutable()).run(environment);
        assertEquals("aabcaaa\nab abc", environment.getOutput());
    }

    @Test
    public void testI() throws LexerException{
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-i","abc", "src/test/resources/file2"),
                Executable.getEmptyCommandExecutable()).run(environment);
        assertEquals("aabcaaa\nab abc\nABc", environment.getOutput());
    }

    @Test
    public void testw() throws LexerException{
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-w", "abc", "src/test/resources/file2"),
                Executable.getEmptyCommandExecutable()).run(environment);
        assertEquals("ab abc", environment.getOutput());
    }

    @Test
    public void testA() throws LexerException{
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-w", "-A", "1", "abc", "src/test/resources/file2"),
                Executable.getEmptyCommandExecutable()).run(environment);
        assertEquals("ab abc\nABc", environment.getOutput());
    }
}