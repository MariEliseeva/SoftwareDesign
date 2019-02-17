package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.Arrays;

import static org.junit.Assert.*;

public class CommandEchoTest {
    @Test
    public void runTest(){
        Environment environment = new Environment();
        CommandCreator.create("echo", Arrays.asList("arb", "hale")).run(environment);
        assertEquals("arb hale" + System.lineSeparator(), environment.getOutput());
    }
}