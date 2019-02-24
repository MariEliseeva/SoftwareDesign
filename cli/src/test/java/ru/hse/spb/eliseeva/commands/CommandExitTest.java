package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommandExitTest {

    @Test
    public void runTest() {
        Environment environment = new Environment();
        assertFalse(environment.isEnd());
        CommandCreator.create("exit", new ArrayList<>()).run(environment);
        assertTrue(environment.isEnd());
    }
}