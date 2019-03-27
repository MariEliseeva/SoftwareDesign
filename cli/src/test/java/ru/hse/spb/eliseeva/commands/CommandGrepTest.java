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
        environment.writeToPipe("some text");
        CommandCreator.create("grep", Arrays.asList("-w", "me text")
        ).run(environment);
        assertFalse(environment.hasOutPut());
        environment.writeToPipe("so me text");
        CommandCreator.create("grep", Arrays.asList("-w", "me text")
        ).run(environment);
        assertEquals("so me text" + System.lineSeparator() , environment.getOutput());
    }

    @Test
    public void testA() {
        Environment environment = new Environment();
        environment.writeToPipe("a" + System.lineSeparator() + "ab"
                + System.lineSeparator() + "cd" + System.lineSeparator() + "ef");
        CommandCreator.create("grep", Arrays.asList("-A", "2", "a")
        ).run(environment);
        assertEquals("a" + System.lineSeparator() + "ab"
                        + System.lineSeparator() + "cd" + System.lineSeparator() + "ef" + System.lineSeparator(),
                environment.getOutput());
    }

    @Test
    public void testAFile() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-w", "-A", "2", "abc", "src/test/resources/file2")
        ).run(environment);
        assertEquals("ab abc" + System.lineSeparator() + "ABc" + System.lineSeparator()
                        + "cde" + System.lineSeparator() + "--" + System.lineSeparator(),
                environment.getOutput());
    }

    @Test
    public void testNoFile() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("abc", "abcd")
        ).run(environment);
        assertEquals("grep: abcd: No such file found." + System.lineSeparator(), environment.getErrors());
    }

    @Test
    public void testInvalidArgument() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-A", "pattern")
        ).run(environment);
        assertEquals("grep: Invalid argument" + System.lineSeparator(), environment.getErrors());
    }

    @Test
    public void testNegativeArgument() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-A", "-10", "pattern")
        ).run(environment);
        assertEquals("grep: Invalid argument" + System.lineSeparator(), environment.getErrors());
    }

    @Test
    public void testInvalidOption() {
        Environment environment = new Environment();
        CommandCreator.create("grep", Arrays.asList("-t", "pattern")
        ).run(environment);
        assertEquals("grep: Invalid option -t" + System.lineSeparator(), environment.getErrors());
    }
}