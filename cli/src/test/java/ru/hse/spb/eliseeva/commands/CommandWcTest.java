package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class CommandWcTest {

    @Test
    public void run() {
        Environment environment = new Environment();
        environment.writeToPipe("1 2 3");
        CommandCreator.create("wc", new ArrayList<>()).run(environment);
        assertEquals("1 3 5", environment.getOutput());
    }
}