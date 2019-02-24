package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommandWcTest {

    @Test
    public void runTest() {
        Environment environment = new Environment();
        environment.writeToPipe("1 2 3" + System.lineSeparator());
        CommandCreator.create("wc", new ArrayList<>()).run(environment);
        assertEquals("1 3 " + (5 + System.lineSeparator().length()) + System.lineSeparator(),
                environment.getOutput());
    }
}