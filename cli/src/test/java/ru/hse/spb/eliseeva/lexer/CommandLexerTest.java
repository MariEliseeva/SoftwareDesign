package ru.hse.spb.eliseeva.lexer;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.List;

import static org.junit.Assert.*;

public class CommandLexerTest {

    @Test
    public void tokenizeTest() throws LexerException {
        List<Token> tokens = new CommandLexer().tokenize("e'ch'o a | c\"at\"");
        assertEquals(Token.Type.TEXT, tokens.get(0).getType());
        assertEquals(Token.Type.SINGLE_QUOTED, tokens.get(1).getType());
        assertEquals(Token.Type.SPACE, tokens.get(3).getType());
        assertEquals(Token.Type.PIPE, tokens.get(6).getType());
        assertEquals(Token.Type.DOUBLE_QUOTED, tokens.get(9).getType());

        assertEquals("e", tokens.get(0).getValue());
        assertEquals("'ch'", tokens.get(1).getValue());
        assertEquals(" ", tokens.get(3).getValue());
        assertEquals("|", tokens.get(6).getValue());
        assertEquals("\"at\"", tokens.get(9).getValue());
    }
}