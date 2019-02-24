package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.Collections;

import static org.junit.Assert.*;

public class CommandExternalTest {

    @Test
    public void runTest() {
        Environment environment = new Environment();
        assertFalse(environment.isEnd());
        CommandCreator.create("echo", Collections.singletonList("12345")).run(environment);
        assertEquals("12345" + System.lineSeparator(), environment.getOutput());
    }
}