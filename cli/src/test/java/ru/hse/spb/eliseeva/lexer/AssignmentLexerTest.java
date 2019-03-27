package ru.hse.spb.eliseeva.lexer;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.List;

import static org.junit.Assert.*;

public class AssignmentLexerTest {

    @Test
    public void tokenizeTest() throws LexerException {
        List<Token> tokens = new AssignmentLexer().tokenize("a\"b\"=25'4'$aa");
        assertEquals(new Token(Token.Type.TEXT, "a"), tokens.get(0));
        assertEquals(new Token(Token.Type.DOUBLE_QUOTED, "\"b\""), tokens.get(1));
        assertEquals(new Token(Token.Type.TEXT, "=25"), tokens.get(2));
        assertEquals(new Token(Token.Type.SINGLE_QUOTED, "'4'"), tokens.get(3));
        assertEquals(new Token(Token.Type.OLD_VARIABLE, "aa"), tokens.get(4));
    }
}