package ru.hse.spb.eliseeva.parser;

import org.junit.Test;
import ru.hse.spb.eliseeva.Environment;
import ru.hse.spb.eliseeva.exceptions.EnvironmentException;
import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.Token;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class CommandsParserTest {

    @Test
    public void parseTest() throws ParserException, EnvironmentException {
        List<Token> tokens = Arrays.asList(
                new Token(Token.Type.DOUBLE_QUOTED, "\"ech\""),
                new Token(Token.Type.SINGLE_QUOTED, "'o'"),
                new Token(Token.Type.SPACE, " "),
                new Token(Token.Type.TEXT, "12"),
                new Token(Token.Type.SPACE, " "),
                new Token(Token.Type.TEXT, "3"),
                new Token(Token.Type.SPACE, " "),
                new Token(Token.Type.PIPE, "|"),
                new Token(Token.Type.TEXT, "cat"),
                new Token(Token.Type.PIPE, "|"),
                new Token(Token.Type.OLD_VARIABLE, "a")

                );
        List<RawCommand> rawCommands = new CommandsParser().parse(tokens);
        assertEquals(rawCommands.get(0).getName().evaluate(new Environment()), "echo");
        assertEquals(rawCommands.get(1).getName().evaluate(new Environment()), "cat");
        Environment environment = new Environment();
        environment.setVariableValue("a", "bcd");
        assertEquals("bcd", rawCommands.get(2).getName().evaluate(environment));
    }

    @Test
    public void parseTest2() throws ParserException, EnvironmentException {
        List<Token> tokens = Collections.singletonList(
                new Token(Token.Type.NEW_VARIABLE, "a=5")
        );
        List<RawCommand> rawCommands = new CommandsParser().parse(tokens);
        assertEquals("=", rawCommands.get(0).getName().evaluate(new Environment()));
    }

    @Test(expected = ParserException.class)
    public void parseBadTest2() throws ParserException {
        List<Token> tokens = Collections.singletonList(
                new Token(Token.Type.NEW_VARIABLE, "a=5'2")
        );
        new CommandsParser().parse(tokens);
    }

    @Test(expected = ParserException.class)
    public void parseBadPipeTest() throws ParserException {
        List<Token> tokens = Arrays.asList(
                new Token(Token.Type.TEXT, "a"),
                new Token(Token.Type.PIPE, "|"),
                new Token(Token.Type.PIPE, "|")
        );
        new CommandsParser().parse(tokens);
    }
}