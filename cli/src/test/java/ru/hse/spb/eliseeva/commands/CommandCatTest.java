package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.Assert.*;

public class CommandCatTest {

    @Test
    public void runTest(){
        Environment environment = new Environment();
        CommandCreator.create("cat", Collections.singletonList("src/test/resources/file1")).run(environment);
        assertEquals("1 2 3 4    5", environment.getOutput());
    }

    @Test
    public void runPipeTest() {
        Environment environment = new Environment();
        environment.writeToPipe("1 2");
        CommandCreator.create("cat", new ArrayList<>()).run(environment);
        assertEquals("1 2", environment.getOutput());
    }
}