package ru.hse.spb.eliseeva.commands;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;

import java.util.Collections;

import static org.junit.Assert.*;

public class CommandCatTest {

    private Environment environment = new Environment();

    @Test
    public void runTest(){
        CommandCreator.create("cat", Collections.singletonList("src/test/resources/file1")).run(environment);
        assertEquals("1 2 3 4    5", environment.getOutput());
    }

    @Test
    public void runPipeTest() {
        environment.writeToPipe("1 2");
        CommandCreator.create("cat", Collections.emptyList()).run(environment);
        assertEquals("1 2", environment.getOutput());
    }

    @Test
    public void runWithWrongFileNameTest() {
        CommandCreator.create("cat", Collections.singletonList("aaa")).run(environment);
        assertEquals("cat: aaa: No such file found." + System.lineSeparator(),
                environment.getErrors());
    }
}