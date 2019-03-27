package ru.hse.spb.eliseeva;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;

import static org.junit.Assert.*;

public class EnvironmentTest {

    private Environment environment = new Environment();

    @Test
    public void setGetVariableValueTest() throws EnvironmentException {
        environment.setVariableValue("a", "25");
        assertEquals("25", environment.getVariableValue("a"));
    }

    @Test(expected = EnvironmentException.class)
    public void getBadVariableValueTest() throws EnvironmentException {
        environment.getVariableValue("a");
    }

    @Test
    public void writeToPipeGetOutputTest() {
        environment.writeToPipe("aaa");
        assertEquals("aaa", environment.getOutput());
        assertFalse(environment.hasOutPut());
        environment.writeToPipe("b");
        assertEquals("b", environment.getOutput());
    }
}