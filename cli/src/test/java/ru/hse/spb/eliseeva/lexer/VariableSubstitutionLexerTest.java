package ru.hse.spb.eliseeva.lexer;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class VariableSubstitutionLexerTest {

    @Test
    public void tokenizeTest() {
        List<Token> tokens = new VariableSubstitutionLexer().tokenize("a\"b\"=25'4'$aa");
        assertEquals(new Token(Token.Type.TEXT, "a\"b\"=25'4'"), tokens.get(0));
        assertEquals(new Token(Token.Type.OLD_VARIABLE, "aa"), tokens.get(1));
    }
}