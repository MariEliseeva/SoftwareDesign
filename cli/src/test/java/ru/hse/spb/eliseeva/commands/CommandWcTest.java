package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class CommandWcTest {

    private Environment environment = new Environment();

    @Test
    public void runTest() {
        environment.writeToPipe("1 2 3" + System.lineSeparator());
        CommandCreator.create("wc", Collections.emptyList()).run(environment);
        assertEquals("1 3 " + (5 + System.lineSeparator().length()) + System.lineSeparator(),
                environment.getOutput());
    }

    @Test
    public void runEmptyTest() {
        CommandCreator.create("wc", Collections.emptyList()).run(environment);
        assertEquals("0 0 0" + System.lineSeparator(),
                environment.getOutput());
    }

    @Test
    public void runFileTest() {
        CommandCreator.create("wc", Arrays.asList("src/test/resources/file1", "src/test/resources/file3"))
                .run(environment);
        assertEquals("1 5 " + (11 + System.lineSeparator().length()) + " src/test/resources/file1" +
                        System.lineSeparator() + "1 0 0 src/test/resources/file3" +
                System.lineSeparator() + "2 5 " + (11 + System.lineSeparator().length()) + " total" +
                System.lineSeparator(), environment.getOutput());
    }

    @Test
    public void runBadFileTest() {
        CommandCreator.create("wc", Collections.singletonList("aaaa"))
                .run(environment);
        assertEquals("wc: aaaa: No such file found." + System.lineSeparator(),
                environment.getErrors());
    }
}