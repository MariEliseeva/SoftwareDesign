package ru.hse.spb.eliseeva.lexer;

import org.junit.Test;
import ru.hse.spb.eliseeva.exceptions.LexerException;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class TokenizerTest {

    @Test
    public void getTokenListTest() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.addSymbol('a');
        tokenizer.addTextToken();
        assertEquals(Collections.singletonList(new Token(Token.Type.TEXT, "a")), tokenizer.getTokenList());
    }

    @Test
    public void tokenizePipeTest() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.addSymbol('a');
        tokenizer.addSymbol('a');
        tokenizer.tokenizePipe();
        assertEquals(Arrays.asList(new Token(Token.Type.TEXT, "aa"), new Token(Token.Type.PIPE, "|")),
                tokenizer.getTokenList());
    }

    @Test
    public void tokenizeVariableAssignmentTest() throws LexerException {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.tokenizeVariableAssignment("a=5", new CommandLexer());
        assertEquals(Collections.singletonList(new Token(Token.Type.NEW_VARIABLE, "a=5")),
                tokenizer.getTokenList());
    }

    @Test
    public void tokenizeDoubleQuotedTest() throws LexerException {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.tokenizeDoubleQuoted(0, "\"a'a'a\"bbcc");
        assertEquals(Collections.singletonList(new Token(Token.Type.DOUBLE_QUOTED, "\"a'a'a\"")),
                tokenizer.getTokenList());
    }

    @Test
    public void tokenizeSingleQuotedTest() throws LexerException {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.tokenizeSingleQuoted(0, "'abc'de");
        assertEquals(Collections.singletonList(new Token(Token.Type.SINGLE_QUOTED, "'abc'")),
                tokenizer.getTokenList());
    }

    @Test
    public void tokenizeOldVariableTest() {
        Tokenizer tokenizer = new Tokenizer();
        tokenizer.tokenizeOldVariable(0, "$abc d");
        assertEquals(Collections.singletonList(new Token(Token.Type.OLD_VARIABLE, "abc")),
                tokenizer.getTokenList());
    }
}