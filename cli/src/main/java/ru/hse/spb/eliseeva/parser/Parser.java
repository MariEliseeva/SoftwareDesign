package ru.hse.spb.eliseeva.parser;

import ru.hse.spb.eliseeva.exceptions.ParserException;
import ru.hse.spb.eliseeva.lexer.Token;

import java.util.List;

public interface Parser {
    Executable parse(List<Token> tokens) throws ParserException;
}
