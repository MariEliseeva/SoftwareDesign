package ru.hse.spb.eliseeva.parser;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.LexerException;
import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.Token;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CommandsParserTest {

    @Test
    public void parse() throws LexerException, ParserException {
        List<Token> tokens = Arrays.asList(
                new Token(Token.Type.DOUBLE_QUOTED, "\"ech\""),
                new Token(Token.Type.SINGLE_QUOTED, "'o'"),
                new Token(Token.Type.SPACE, " "),
                new Token(Token.Type.TEXT, "12"),
                new Token(Token.Type.SPACE, " "),
                new Token(Token.Type.TEXT, "3"),
                new Token(Token.Type.SPACE, " "),
                new Token(Token.Type.PIPE, "|"),
                new Token(Token.Type.TEXT, "cat")
                );
        Executable executable = new CommandsParser().parse(tokens);
        assertEquals(executable.getCommandName().evaluate(new Environment()), "cat");
        assertEquals(executable.getPreviousCommand().getCommandName().evaluate(new Environment()), "echo");
    }
}