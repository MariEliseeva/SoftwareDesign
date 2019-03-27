package ru.hse.spb.eliseeva.lexer;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.List;

import static org.junit.Assert.*;

public class CommandLexerTest {

    @Test
    public void tokenizeTest() throws LexerException {
        List<Token> tokens = new CommandLexer().tokenize("e'ch'o a | c\"at\" $a");
        assertEquals(new Token(Token.Type.TEXT, "e"), tokens.get(0));
        assertEquals(new Token(Token.Type.SINGLE_QUOTED, "'ch'"), tokens.get(1));
        assertEquals(new Token(Token.Type.SPACE, " "),  tokens.get(3));
        assertEquals(new Token(Token.Type.PIPE, "|"),  tokens.get(6));
        assertEquals(new Token(Token.Type.DOUBLE_QUOTED, "\"at\""),  tokens.get(9));
        assertEquals(new Token(Token.Type.OLD_VARIABLE, "a"),  tokens.get(11));
    }

    @Test
    public void tokenizeTest2() throws LexerException {
        List<Token> tokens = new CommandLexer().tokenize("a=12'3'\"4\"5");
        assertEquals(new Token(Token.Type.NEW_VARIABLE, "a=12'3'\"4\"5"), tokens.get(0));
    }

    @Test(expected = LexerException.class)
    public void tokenizeTest3() throws LexerException {
        new CommandLexer().tokenize("a=12'3'\"4\"5 | b = 4");
    }
}