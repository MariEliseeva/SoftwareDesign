package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class CommandExitTest {

    @Test
    public void runTest() {
        Environment environment = new Environment();
        assertFalse(environment.isEnd());
        CommandCreator.create("exit", Collections.emptyList()).run(environment);
        assertTrue(environment.isEnd());
    }
}