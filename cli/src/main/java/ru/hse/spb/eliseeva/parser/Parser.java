package ru.hse.spb.eliseeva.parser;

import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.Token;

import java.util.List;

/**
 * Interface to parse tokens and create executable objects.
 */
public interface Parser {
    /**
     * Goes through the tokens and divide them into different commands that can be executed later.
     * @param tokens list of tokens to transform to raw commands
     * @return list of raw commands
     * @throws ParserException thrown if wrong pipe usage found or some other problems with parsing
     */
    List<RawCommand> parse(List<Token> tokens) throws ParserException;
}
