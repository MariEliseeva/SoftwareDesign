package ru.hse.spb.eliseeva.substitution;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;
import ru.hse.spb.eliseeva.lexer.Token;

import static org.junit.Assert.*;

public class VariableWordPartTest {

    @Test
    public void evaluateTest() throws EnvironmentException {
        WordPart wordPart = WordPartCreator.create(new Token(Token.Type.OLD_VARIABLE, "ppp"));
        Environment environment = new Environment();
        environment.setVariableValue("ppp", "aaa");
        assert wordPart != null;
        assertEquals("aaa", wordPart.evaluate(environment));
    }
}