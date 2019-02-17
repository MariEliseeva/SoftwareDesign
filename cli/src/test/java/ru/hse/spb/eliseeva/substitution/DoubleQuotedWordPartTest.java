package ru.hse.spb.eliseeva.substitution;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;
import ru.hse.spb.eliseeva.lexer.Token;

import static org.junit.Assert.*;

public class DoubleQuotedWordPartTest {

    @Test
    public void evaluateTest() throws EnvironmentException {
        WordPart wordPart = WordPartCreator.create(new Token(Token.Type.DOUBLE_QUOTED, "\"   '$t'  \""));
        Environment environment = new Environment();
        environment.setVariableValue("t", "text");
        assert wordPart != null;
        assertEquals("   'text'  ", wordPart.evaluate(environment));
    }
}