package ru.hse.spb.eliseeva.substitution;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;
import ru.hse.spb.eliseeva.lexer.Token;

import static org.junit.Assert.*;

public class WordTest {
    @Test
    public void evaluateTest() throws EnvironmentException {
        Word word = Word.getEmptyWord();
        Environment environment = new Environment();
        environment.setVariableValue("ppp", "aaa");
        environment.setVariableValue("t", "text");
        word.addPart(WordPartCreator.create(new Token(Token.Type.DOUBLE_QUOTED, "\"   '$t'  \"")));
        word.addPart(WordPartCreator.create(new Token(Token.Type.TEXT, "bbb")));
        word.addPart(WordPartCreator.create(new Token(Token.Type.OLD_VARIABLE, "ppp")));
        assertEquals("   'text'  bbbaaa", word.evaluate(environment));
    }
}