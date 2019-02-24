package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CommandGrepTest {

    @Test
    public void run() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("abc", "src/test/resources/file2")).run(environment);
        assertEquals("aabcaaa" + System.lineSeparator() + "ab abc" + System.lineSeparator(),
                environment.getOutput());
    }

    @Test
    public void testI() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-i","abc", "src/test/resources/file2")
        ).run(environment);
        assertEquals("aabcaaa" + System.lineSeparator() + "ab abc" + System.lineSeparator() + "ABc" +
                System.lineSeparator(), environment.getOutput());
    }

    @Test
    public void testw() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-w", "abc", "src/test/resources/file2")
        ).run(environment);
        assertEquals("ab abc" + System.lineSeparator() , environment.getOutput());
    }

    @Test
    public void testA() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-w", "-A", "1", "abc", "src/test/resources/file2")
        ).run(environment);
        assertEquals("ab abc" + System.lineSeparator() + "ABc" + System.lineSeparator(),
                environment.getOutput());
    }
}