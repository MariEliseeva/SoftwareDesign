package ru.hse.spb.eliseeva.parser;

import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.Token;

import java.util.List;

/**
 * Interface to parse tokens and create executable objects.
 */
public interface Parser {
    List<RawCommand> parse(List<Token> tokens) throws ParserException;
}
