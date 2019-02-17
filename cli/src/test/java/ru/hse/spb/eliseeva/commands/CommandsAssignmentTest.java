package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CommandsAssignmentTest {

    @Test
    public void run() {
        Environment environment = new Environment();
        CommandCreator.create("=", Arrays.asList("a", "echo")).run(environment);
        assertEquals("echo", environment.getVariableValue("a"));
    }
}